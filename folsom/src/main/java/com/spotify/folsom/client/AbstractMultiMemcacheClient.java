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
package com.spotify.folsom.client;

import com.google.common.base.Preconditions;
import com.spotify.folsom.AbstractRawMemcacheClient;
import com.spotify.folsom.ConnectionChangeListener;
import com.spotify.folsom.ObservableClient;
import com.spotify.folsom.RawMemcacheClient;
import com.spotify.folsom.ketama.AddressAndClient;
import java.util.Collection;
import java.util.stream.Stream;

public abstract class AbstractMultiMemcacheClient extends AbstractRawMemcacheClient implements ConnectionChangeListener {

    protected final Collection<RawMemcacheClient> clients;

    public AbstractMultiMemcacheClient(final Collection<RawMemcacheClient> clients) {
        Preconditions.checkArgument(!clients.isEmpty(), "clients must not be empty");
        this.clients = clients;
        for (RawMemcacheClient client : clients) {
            client.registerForConnectionChanges(this);
        }
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
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void connectionChanged(ObservableClient client) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Throwable getConnectionFailure() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
