/*
 * Copyright (c) 2023 Spotify AB
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
package com.spotify.folsom.reconnect;

import com.spotify.folsom.guava.HostAndPort;
import java.util.Objects;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link ReconnectionListener} which ensures the methods never throw {@link Exception} (though
 * {@link Error} and {@link Throwable} are not caught).
 *
 * <p>This should wrap any untrusted {@link ReconnectionListener}.
 *
 * <p>This class should be regarded as <b>internal API</b>. We recognise it may be useful outside
 * internals, to which end it is exposed. The constructor is <b>stable</b>. All methods are
 * <b>unstable</b> and can change with breakage in patch versions. If you want a stable interface,
 * wrap your own reconnection listener in this, instead of extending it (and instead extend {@link
 * AbstractReconnectionListener}).
 */
public class CatchingReconnectionListener implements ReconnectionListener {

    private static final Logger log = LoggerFactory.getLogger(CatchingReconnectionListener.class);

    private final ReconnectionListener delegate;

    public CatchingReconnectionListener(final ReconnectionListener delegate) {
        this.delegate = Objects.requireNonNull(delegate, "delegate must not be null");
    }

    @Override
    public void connectionFailure(final Throwable cause) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void reconnectionCancelled() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void reconnectionSuccessful(final HostAndPort address, final int attempt, final boolean willStayConnected) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void connectionLost(@Nullable final Throwable cause, final HostAndPort address) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void reconnectionQueuedFromError(final Throwable cause, final HostAndPort address, final long backOffMillis, final int attempt) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean equals(final Object o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
