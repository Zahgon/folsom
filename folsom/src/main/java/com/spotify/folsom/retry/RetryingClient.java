/*
 * Copyright (c) 2014-2015 Spotify AB
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
package com.spotify.folsom.retry;

import static com.spotify.folsom.client.Utils.unwrap;
import com.spotify.folsom.ConnectionChangeListener;
import com.spotify.folsom.MemcacheClosedException;
import com.spotify.folsom.RawMemcacheClient;
import com.spotify.folsom.client.Request;
import com.spotify.folsom.ketama.AddressAndClient;
import com.spotify.futures.CompletableFutures;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * A simple wrapping client that retries once (but only for MemcacheClosedException's). This helps
 * avoid some transient problems when a node suddenly stops. It's mostly useful in combination with
 * a client that internally routes to multiple nodes such as the Ketama client or RoundRobin client.
 * It won't prevent all MemcacheClosedException's from propagating, it will just reduce the
 * frequency in some cases.
 *
 * <p>The retrying is intentionally strict about when to retry and how many times to retries in
 * order to minimize risk of causing more problems then it would solve.
 */
public class RetryingClient implements RawMemcacheClient {

    private final RawMemcacheClient delegate;

    public RetryingClient(final RawMemcacheClient delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T> CompletionStage<T> send(final Request<T> request) {
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

    @Override
    public void registerForConnectionChanges(ConnectionChangeListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void unregisterForConnectionChanges(ConnectionChangeListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void notifyConnectionChange() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
