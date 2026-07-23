/*
 * Copyright (c) 2014-2023 Spotify AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.spotify.folsom;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.spotify.folsom.client.MemcacheEncoder.MAX_KEY_LEN;
import static java.util.Objects.requireNonNull;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.spotify.dns.DnsSrvResolver;
import com.spotify.folsom.authenticate.AsciiAuthenticationValidator;
import com.spotify.folsom.authenticate.AsciiAuthenticator;
import com.spotify.folsom.authenticate.Authenticator;
import com.spotify.folsom.authenticate.BinaryAuthenticationValidator;
import com.spotify.folsom.authenticate.MultiAuthenticator;
import com.spotify.folsom.authenticate.NoAuthenticationValidation;
import com.spotify.folsom.authenticate.PlaintextAuthenticator;
import com.spotify.folsom.authenticate.UsernamePasswordPair;
import com.spotify.folsom.client.NoopMetrics;
import com.spotify.folsom.client.NoopTracer;
import com.spotify.folsom.client.ascii.DefaultAsciiMemcacheClient;
import com.spotify.folsom.client.binary.DefaultBinaryMemcacheClient;
import com.spotify.folsom.client.tls.SSLEngineFactory;
import com.spotify.folsom.guava.HostAndPort;
import com.spotify.folsom.ketama.AddressAndClient;
import com.spotify.folsom.ketama.Continuum;
import com.spotify.folsom.ketama.KetamaMemcacheClient;
import com.spotify.folsom.ketama.NodeLocator;
import com.spotify.folsom.ketama.ResolvingKetamaClient;
import com.spotify.folsom.reconnect.CatchingReconnectionListener;
import com.spotify.folsom.reconnect.ReconnectingClient;
import com.spotify.folsom.reconnect.ReconnectionListener;
import com.spotify.folsom.retry.RetryingClient;
import com.spotify.folsom.roundrobin.RoundRobinMemcacheClient;
import com.spotify.folsom.transcoder.ByteArrayTranscoder;
import com.spotify.folsom.transcoder.SerializableObjectTranscoder;
import com.spotify.folsom.transcoder.StringTranscoder;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MemcacheClientBuilder<V> {

    private static final int DEFAULT_MAX_SET_LENGTH = 1024 * 1024;

    private static final int DEFAULT_MAX_OUTSTANDING = 1000;

    private static final String DEFAULT_HOSTNAME = "127.0.0.1";

    private static final int DEFAULT_PORT = 11211;

    /**
     * Lazily instantiated singleton default executor.
     */
    private static final Supplier<Executor> DEFAULT_REPLY_EXECUTOR = Suppliers.memoize(() -> new ForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, new UncaughtExceptionHandler(), true))::get;

    /**
     * Lazily instantiated singleton default scheduled executor.
     */
    private static final Supplier<ScheduledExecutorService> DEFAULT_SCHEDULED_EXECUTOR = Suppliers.memoize(() -> Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setDaemon(true).setNameFormat("folsom-default-scheduled-executor").build()))::get;

    private final List<HostAndPort> addresses = new ArrayList<>();

    private int maxOutstandingRequests = DEFAULT_MAX_OUTSTANDING;

    private int eventLoopThreadFlushMaxBatchSize = Settings.DEFAULT_BATCH_SIZE;

    private final Transcoder<V> valueTranscoder;

    private Metrics metrics = NoopMetrics.INSTANCE;

    private Tracer tracer = NoopTracer.INSTANCE;

    private ReconnectionListener reconnectionListener = new ReconnectingClient.StandardReconnectionListener();

    private BackoffFunction backoffFunction = new ExponentialBackoff(10L, 60 * 1000L, 2.5);

    private int connections = 1;

    private boolean retry = true;

    private Supplier<Executor> executor = DEFAULT_REPLY_EXECUTOR;

    private Charset charset = StandardCharsets.UTF_8;

    private Resolver resolver;

    // deprecated, retained for backwards compatibility
    private DnsSrvResolver srvResolver;

    // deprecated, retained for backwards compatibility
    private String srvRecord;

    private long resolveRefreshPeriod = 60 * 1000L;

    private long shutdownDelay = 60 * 1000L;

    private long connectionTimeoutMillis = 3000;

    private int maxSetLength = DEFAULT_MAX_SET_LENGTH;

    private int maxKeyLength = MAX_KEY_LEN;

    private EventLoopGroup eventLoopGroup;

    private Class<? extends Channel> channelClass;

    private final List<UsernamePasswordPair> passwords = new ArrayList<>();

    private boolean skipAuth = false;

    private Function<Collection<AddressAndClient>, NodeLocator> nodeLocator = Continuum::new;

    private SSLEngineFactory sslEngineFactory = null;

    public static MemcacheClientBuilder<byte[]> newByteArrayClient() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MemcacheClientBuilder<String> newStringClient() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MemcacheClientBuilder<String> newStringClient(Charset charset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <T extends Serializable> MemcacheClientBuilder<T> newSerializableObjectClient() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a client builder with the provided value transcoder.
     *
     * @param valueTranscoder the transcoder to use to encode/decode values.
     */
    public MemcacheClientBuilder(final Transcoder<V> valueTranscoder) {
        this.valueTranscoder = valueTranscoder;
    }

    public MemcacheClientBuilder<V> withKeyCharset(final Charset charset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withAddress(final String hostname) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withAddress(final String host, final int port) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withResolver(final Resolver resolver) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Used for backwards compatibility for srvRecord and srvResolver.
     */
    private void updateResolver() {
        // if the srvRecord is not provided, do not create a resolver
        if (srvRecord != null) {
            final SrvResolver.Builder builder = SrvResolver.newBuilder(srvRecord);
            if (srvResolver != null) {
                builder.withSrvResolver(srvResolver);
            }
            this.resolver = builder.build();
        } else {
            this.resolver = null;
        }
    }

    /**
     * @deprecated Use {@link #withResolver(Resolver)} with {@link SrvResolver} instead.
     */
    @Deprecated
    public MemcacheClientBuilder<V> withSRVRecord(final String srvRecord) {
        this.srvRecord = requireNonNull(srvRecord);
        updateResolver();
        return this;
    }

    public MemcacheClientBuilder<V> withResolveRefreshPeriod(final long periodMillis) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @deprecated Use {@link #withResolveRefreshPeriod(long)}
     */
    @Deprecated
    public MemcacheClientBuilder<V> withSRVRefreshPeriod(final long periodMillis) {
        return withResolveRefreshPeriod(periodMillis);
    }

    public MemcacheClientBuilder<V> withResolveShutdownDelay(final long shutdownDelay) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @deprecated Use {@link #withResolveShutdownDelay(long)}
     */
    @Deprecated
    public MemcacheClientBuilder<V> withSRVShutdownDelay(final long shutdownDelay) {
        return withResolveShutdownDelay(shutdownDelay);
    }

    /**
     * @deprecated Use {@link #withResolver(Resolver)} with {@link SrvResolver} instead
     */
    @Deprecated
    public MemcacheClientBuilder<V> withSrvResolver(final DnsSrvResolver srvResolver) {
        this.srvResolver = requireNonNull(srvResolver, "srvResolver");
        updateResolver();
        return this;
    }

    public MemcacheClientBuilder<V> withMetrics(final Metrics metrics) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withTracer(final Tracer tracer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withReconnectionListener(final ReconnectionListener reconnectionListener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withMaxOutstandingRequests(final int maxOutstandingRequests) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Specify the maximum number of requests that will be written to a connection in a single
     * operation when sending requests on the {@link EventLoopGroup} thread that is handling the
     * connection.
     *
     * <p>Note: The name of this configuration property is misleading.
     *
     * <p>Configuring this is only useful in specific circumstances when also using {@link
     * #withEventLoopGroup(EventLoopGroup)} to configure an IO thread pool that is also used to send
     * requests. E.g. when reusing the same {@link EventLoopGroup} for handling incoming network IO
     * and sending memcache requests in a service.
     *
     * @see #withEventLoopThreadFlushMaxBatchSize(int)
     * @param eventLoopThreadFlushMaxBatchSize the maximum number of requests that will be written to
     *     a connection in a single operation when sending requests on the {@link EventLoopGroup}
     *     thread that is handling the connection. Default is {@value Settings#DEFAULT_BATCH_SIZE}.
     * @return itself
     * @deprecated Most users should prefer {@link #withMaxOutstandingRequests(int)}. Some users that
     *     also configure {@link #withEventLoopGroup(EventLoopGroup)} might want to configure {@link
     *     #withEventLoopThreadFlushMaxBatchSize(int)}.
     */
    @Deprecated
    public MemcacheClientBuilder<V> withRequestBatchSize(final int eventLoopThreadFlushMaxBatchSize) {
        return withEventLoopThreadFlushMaxBatchSize(eventLoopThreadFlushMaxBatchSize);
    }

    public MemcacheClientBuilder<V> withEventLoopThreadFlushMaxBatchSize(final int eventLoopThreadFlushMaxBatchSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withBackoff(final BackoffFunction backoffFunction) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withRetry(final boolean retry) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withReplyExecutor(final Executor executor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withConnections(final int connections) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This has been deprecated - see {#link withConnectionTimeoutMillis}.
     *
     * <p>Do not use this to enforce request timeouts. Instead, set a timeout on the request futures
     * using orTimeout() or some other manual mechanism.
     *
     * @param timeoutMillis The timeout in milliseconds. The default is 3000 ms.
     * @return itself
     */
    @Deprecated
    public MemcacheClientBuilder<V> withRequestTimeoutMillis(final long timeoutMillis) {
        return withConnectionTimeoutMillis(timeoutMillis);
    }

    public MemcacheClientBuilder<V> withConnectionTimeoutMillis(final long timeoutMillis) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withMaxSetLength(final int maxSetLength) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withEventLoopGroup(final EventLoopGroup eventLoopGroup) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withChannelClass(final Class<? extends Channel> channelClass) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withMaxKeyLength(final int maxKeyLength) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withUsernamePassword(final String username, final String password) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withNodeLocator(Function<Collection<AddressAndClient>, NodeLocator> nodeLocator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    MemcacheClientBuilder<V> withoutAuthenticationValidation() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MemcacheClientBuilder<V> withSSLEngineFactory(final SSLEngineFactory sslEngineFactory) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public BinaryMemcacheClient<V> connectBinary() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Authenticator getAuthenticator(Authenticator defaultValue) {
        if (skipAuth) {
            if (!passwords.isEmpty()) {
                throw new IllegalStateException("You may not specify both withoutAuthenticationValidation() and withUsernamePassword()");
            }
            return NoAuthenticationValidation.getInstance();
        }
        if (passwords.isEmpty()) {
            return defaultValue;
        }
        if (defaultValue instanceof BinaryAuthenticationValidator) {
            List<PlaintextAuthenticator> authenticatorList = passwords.stream().map(UsernamePasswordPair::getPlainTextAuthenticator).collect(Collectors.toList());
            return new MultiAuthenticator(authenticatorList);
        } else if (defaultValue instanceof AsciiAuthenticationValidator) {
            List<AsciiAuthenticator> authenticatorList = passwords.stream().map(UsernamePasswordPair::getAsciiAuthenticator).collect(Collectors.toList());
            return new MultiAuthenticator(authenticatorList);
        }
        throw new IllegalStateException("Only ASCII and binary protocols support authentication.");
    }

    public AsciiMemcacheClient<V> connectAscii() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected RawMemcacheClient connectRaw(boolean binary, Authenticator authenticator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private List<RawMemcacheClient> createClients(final List<HostAndPort> addresses, final boolean binary, final Authenticator authenticator) {
        final List<RawMemcacheClient> clients = new ArrayList<>(addresses.size());
        for (final HostAndPort address : addresses) {
            clients.add(createClient(address, binary, authenticator));
        }
        return clients;
    }

    private RawMemcacheClient createResolvingClient(final boolean binary, final Authenticator authenticator) {
        ResolvingKetamaClient client = new ResolvingKetamaClient(resolver, DEFAULT_SCHEDULED_EXECUTOR.get(), resolveRefreshPeriod, TimeUnit.MILLISECONDS, input -> createClient(input, binary, authenticator), shutdownDelay, TimeUnit.MILLISECONDS, nodeLocator);
        client.start();
        return client;
    }

    private RawMemcacheClient createClient(final HostAndPort address, final boolean binary, final Authenticator authenticator) {
        if (connections == 1) {
            return createReconnectingClient(address, binary, authenticator);
        }
        final List<RawMemcacheClient> clients = new ArrayList<>();
        for (int i = 0; i < connections; i++) {
            clients.add(createReconnectingClient(address, binary, authenticator));
        }
        return new RoundRobinMemcacheClient(clients);
    }

    private RawMemcacheClient createReconnectingClient(final HostAndPort address, final boolean binary, final Authenticator authenticator) {
        return new ReconnectingClient(backoffFunction, ReconnectingClient.singletonExecutor(), address, reconnectionListener, maxOutstandingRequests, eventLoopThreadFlushMaxBatchSize, binary, authenticator, executor.get(), connectionTimeoutMillis, charset, metrics, maxSetLength, eventLoopGroup, channelClass, sslEngineFactory);
    }
}
