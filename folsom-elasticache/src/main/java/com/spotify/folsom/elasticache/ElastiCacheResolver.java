/*
 * Copyright (c) 2014-2019 Spotify AB
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
package com.spotify.folsom.elasticache;

import static com.google.common.base.Preconditions.checkArgument;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.stream.Collectors.toList;
import com.spotify.folsom.Resolver;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ssl.SSLSocketFactory;

/**
 * Implement support for AWS ElastiCache node auto-discovery <a
 * href="https://docs.aws.amazon.com/AmazonElastiCache/latest/mem-ug/AutoDiscovery.AddingToYourClientLibrary.html">as
 * documented</a>.
 *
 * <p>Example use:
 *
 * <pre>{@code
 * ElastiCacheResolver resolver = ElastiCacheResolver
 *          .newBuilder("cluster-configuration-endpoint-hostname")
 *          .build();
 *
 *  MemcacheClientBuilder.newStringClient()
 *          .withResolver(resolver)
 *          ...
 * }</pre>
 */
public class ElastiCacheResolver implements Resolver {

    public static class Builder {

        private final String configHost;

        private int configPort = 11211;

        private long ttl = MINUTES.toMillis(1);

        // ms
        private int timeout = 5000;

        private boolean useTls = false;

        private Builder(final String configHost) {
            this.configHost = configHost;
        }

        public Builder withConfigPort(final int port) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder withTtlMillis(final long ttl) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder withResolveTimeoutMillis(final int timeout) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder withTls(final boolean useTls) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public ElastiCacheResolver build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    private static final byte[] CMD = "config get cluster\n".getBytes(US_ASCII);

    public static Builder newBuilder(final String configHost) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private final Resolver resolver;

    private final long ttl;

    private final AtomicReference<Response> currentResponse = new AtomicReference<>();

    ElastiCacheResolver(final Resolver resolver, final long ttl) {
        this.resolver = resolver;
        this.ttl = ttl;
    }

    @Override
    public List<ResolveResult> resolve() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public interface Resolver {

        Response resolve();
    }

    public static class SocketResolver implements Resolver {

        private final String configHost;

        private final int configPort;

        private final int timeout;

        private final boolean useTls;

        private final ResponseParser parser = new ResponseParser();

        public SocketResolver(final String configHost, final int configPort, final int timeout, final boolean useTls) {
            this.configHost = configHost;
            this.configPort = configPort;
            this.timeout = timeout;
            this.useTls = useTls;
        }

        public Response resolve() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private Socket createSocket() throws IOException {
            if (useTls) {
                return SSLSocketFactory.getDefault().createSocket();
            } else {
                return new Socket();
            }
        }
    }
}
