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
package com.spotify.folsom;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import java.util.List;
import java.util.Objects;

public interface Resolver {

    class ResolveResult {

        private final String host;

        private final int port;

        private final long ttl;

        public ResolveResult(String host, int port, long ttl) {
            this.host = requireNonNull(host);
            checkArgument(port > 0, "port must be a positive integer");
            this.port = port;
            checkArgument(ttl > 0, "ttl must be a positive integer");
            this.ttl = ttl;
        }

        public String getHost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public int getPort() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public long getTtl() {
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

    List<ResolveResult> resolve();
}
