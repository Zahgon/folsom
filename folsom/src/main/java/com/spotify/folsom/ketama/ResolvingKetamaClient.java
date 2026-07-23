/*
 * Copyright (c) 2015 Spotify AB
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
package com.spotify.folsom.ketama;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.spotify.folsom.AbstractRawMemcacheClient;
import com.spotify.folsom.ConnectionChangeListener;
import com.spotify.folsom.ObservableClient;
import com.spotify.folsom.RawMemcacheClient;
import com.spotify.folsom.Resolver;
import com.spotify.folsom.client.NotConnectedClient;
import com.spotify.folsom.client.Request;
import com.spotify.folsom.guava.HostAndPort;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResolvingKetamaClient extends AbstractRawMemcacheClient {

    private static final Logger log = LoggerFactory.getLogger(ResolvingKetamaClient.class);

    public static final int MIN_RESOLVE_WAIT_TIME = 10;

    public static final int MAX_RESOLVE_WAIT_TIME = 3600;

    private final ScheduledExecutorService executor;

    private final Resolver resolver;

    private final long ttl;

    private final Connector connector;

    private final long shutdownDelay;

    private final TimeUnit shutdownUnit;

    private final MyConnectionChangeListener listener = new MyConnectionChangeListener();

    private ScheduledFuture<?> refreshJob;

    private final Object sync = new Object();

    private final Map<HostAndPort, RawMemcacheClient> clients = new HashMap<>();

    private final Collection<RawMemcacheClient> shutdownQueue = new ArrayList<>();

    private volatile RawMemcacheClient currentClient;

    private volatile RawMemcacheClient pendingClient = null;

    private boolean shutdown = false;

    private final Function<Collection<AddressAndClient>, NodeLocator> nodeLocator;

    public ResolvingKetamaClient(Resolver resolver, ScheduledExecutorService executor, long period, TimeUnit periodUnit, final Connector connector, long shutdownDelay, TimeUnit shutdownUnit, Function<Collection<AddressAndClient>, NodeLocator> nodeLocator) {
        this.resolver = resolver;
        this.connector = connector;
        this.shutdownDelay = shutdownDelay;
        this.shutdownUnit = shutdownUnit;
        this.executor = executor;
        this.currentClient = NotConnectedClient.INSTANCE;
        this.ttl = TimeUnit.SECONDS.convert(period, periodUnit);
        this.nodeLocator = nodeLocator;
    }

    public void start() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void resolve() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private long clamp(int min, int max, long value) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    public <T> CompletionStage<T> send(Request<T> request) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void shutdown() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isConnected() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Throwable getConnectionFailure() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int numTotalConnections() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int numActiveConnections() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int numPendingRequests() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Stream<AddressAndClient> streamNodes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public interface Connector {

        RawMemcacheClient connect(HostAndPort input);
    }

    private void setPendingClient(final ImmutableList.Builder<RawMemcacheClient> removedClients) {
        shutdownQueue.addAll(removedClients.build());
        final List<AddressAndClient> addressAndClients = clients.entrySet().stream().map(e -> new AddressAndClient(e.getKey(), e.getValue())).collect(Collectors.toList());
        // This may invalidate an existing pendingClient but should be fine since it doesn't have any
        // important state of its own.
        final KetamaMemcacheClient newClient = new KetamaMemcacheClient(addressAndClients, nodeLocator.apply(addressAndClients));
        this.pendingClient = newClient;
        newClient.connectFuture().thenRun(() -> {
            final ImmutableList<RawMemcacheClient> shutdownJob;
            synchronized (sync) {
                if (pendingClient != newClient) {
                    // We don't care about this event if it's not the expected client
                    return;
                }
                currentClient = newClient;
                pendingClient = null;
                shutdownJob = ImmutableList.copyOf(shutdownQueue);
                shutdownQueue.clear();
            }
            executor.schedule(() -> shutdownJob.forEach(RawMemcacheClient::shutdown), shutdownDelay, shutdownUnit);
            notifyConnectionChange();
        });
    }

    private class MyConnectionChangeListener implements ConnectionChangeListener {

        @Override
        public void connectionChanged(ObservableClient client) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
