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
package com.spotify.folsom.client.test;

import com.spotify.folsom.AbstractRawMemcacheClient;
import com.spotify.folsom.GetResult;
import com.spotify.folsom.MemcacheClosedException;
import com.spotify.folsom.MemcacheStatus;
import com.spotify.folsom.Metrics;
import com.spotify.folsom.client.GetRequest;
import com.spotify.folsom.client.MultiRequest;
import com.spotify.folsom.client.NoopMetrics;
import com.spotify.folsom.client.Request;
import com.spotify.folsom.client.SetRequest;
import com.spotify.folsom.client.ascii.DeleteRequest;
import com.spotify.folsom.client.ascii.IncrRequest;
import com.spotify.folsom.client.ascii.TouchRequest;
import com.spotify.folsom.guava.HostAndPort;
import com.spotify.folsom.ketama.AddressAndClient;
import com.spotify.futures.CompletableFutures;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public class FakeRawMemcacheClient extends AbstractRawMemcacheClient {

    private boolean connected = true;

    private final Map<ByteBuffer, byte[]> map = new HashMap<>();

    private int outstanding = 0;

    private final String address;

    private Throwable failure;

    public FakeRawMemcacheClient() {
        this(new NoopMetrics());
    }

    public FakeRawMemcacheClient(final Metrics metrics) {
        this(metrics, "address:123");
    }

    public FakeRawMemcacheClient(final Metrics metrics, final String address) {
        metrics.registerOutstandingRequestsGauge(() -> outstanding);
        this.address = address;
    }

    @Override
    public <T> CompletionStage<T> send(Request<T> request) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void shutdown() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setConnected() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setFailure(Throwable failure) {
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

    public Map<ByteBuffer, byte[]> getMap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setOutstandingRequests(int outstanding) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
