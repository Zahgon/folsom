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

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Function;

public final class Utils {

    private static final int TTL_CUTOFF = 60 * 60 * 24 * 30;

    private Utils() {
    }

    public static int ttlToExpiration(final int ttl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <T> Function<List<List<T>>, List<T>> flatten() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static int getGlobalConnectionCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Throwable unwrap(final Throwable e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static <T> CompletionStage<T> onExecutor(CompletionStage<T> future, Executor executor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <K, V> Map<K, V> zipToMap(final List<K> keys, final List<V> values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
