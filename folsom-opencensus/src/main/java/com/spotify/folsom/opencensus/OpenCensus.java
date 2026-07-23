/*
 * Copyright (c) 2019 Spotify AB
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
package com.spotify.folsom.opencensus;

import com.spotify.folsom.Tracer;

/**
 * Starting point for the OpenCensus tracing implementation for Folsom
 */
public class OpenCensus {

    private static final boolean DEFAULT_INCLUDE_KEYS = true;

    private static final boolean DEFAULT_INCLUDE_VALUES = false;

    public static Tracer tracer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static class Builder {

        private boolean includeKeys = DEFAULT_INCLUDE_KEYS;

        private boolean includeValues = DEFAULT_INCLUDE_VALUES;

        public Builder withIncludeKeys(final boolean includeKeys) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder withIncludeValues(final boolean includeValues) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Tracer build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
