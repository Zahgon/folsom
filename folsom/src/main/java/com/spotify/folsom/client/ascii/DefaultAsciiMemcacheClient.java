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
package com.spotify.folsom.client.ascii;

import static com.spotify.folsom.client.AbstractRequest.encodeKey;
import static com.spotify.folsom.client.AbstractRequest.encodeKeys;
import static java.util.Objects.requireNonNull;
import com.google.common.collect.Lists;
import com.spotify.folsom.AsciiMemcacheClient;
import com.spotify.folsom.ConnectionChangeListener;
import com.spotify.folsom.GetResult;
import com.spotify.folsom.MemcacheStatus;
import com.spotify.folsom.MemcachedStats;
import com.spotify.folsom.Metrics;
import com.spotify.folsom.RawMemcacheClient;
import com.spotify.folsom.Tracer;
import com.spotify.folsom.Transcoder;
import com.spotify.folsom.client.MemcacheEncoder;
import com.spotify.folsom.client.TransformerUtil;
import com.spotify.folsom.client.Utils;
import com.spotify.futures.CompletableFutures;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * The default implementation of {@link com.spotify.folsom.AsciiMemcacheClient}
 *
 * @param <V> The value type for all operations
 */
public class DefaultAsciiMemcacheClient<V> implements AsciiMemcacheClient<V> {

    private final RawMemcacheClient rawMemcacheClient;

    private final Metrics metrics;

    private final Tracer tracer;

    private final Transcoder<V> valueTranscoder;

    private final TransformerUtil<V> transformerUtil;

    private final Charset charset;

    private final int maxKeyLength;

    private DefaultAsciiMemcacheClient(final RawMemcacheClient rawMemcacheClient, final Metrics metrics, final Tracer tracer, final Transcoder<V> valueTranscoder, final TransformerUtil<V> transformerUtil, final Charset charset, final int maxKeyLength) {
        this.rawMemcacheClient = rawMemcacheClient;
        this.metrics = metrics;
        this.tracer = tracer;
        this.valueTranscoder = valueTranscoder;
        this.charset = charset;
        this.transformerUtil = transformerUtil;
        this.maxKeyLength = maxKeyLength;
    }

    public DefaultAsciiMemcacheClient(final RawMemcacheClient rawMemcacheClient, final Metrics metrics, final Tracer tracer, final Transcoder<V> valueTranscoder, final Charset charset, final int maxKeyLength) {
        this(rawMemcacheClient, metrics, tracer, valueTranscoder, new TransformerUtil<>(valueTranscoder), charset, maxKeyLength);
    }

    @Override
    public CompletionStage<MemcacheStatus> set(final String key, final V value, final int ttl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> set(final String key, final V value, final int ttl, final int flags) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> set(String key, V value, int ttl, long cas) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> set(String key, V value, int ttl, long cas, int flags) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> delete(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> delete(String key, long cas) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> deleteAll(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> add(String key, V value, int ttl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> add(String key, V value, int ttl, int flags) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> replace(String key, V value, int ttl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> replace(String key, V value, int ttl, int flags) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> append(String key, V value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> prepend(String key, V value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<Long> incr(String key, long by) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<Long> decr(String key, long by) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<V> get(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<GetResult<V>> casGet(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private CompletionStage<GetResult<V>> get(final String key, final boolean withCas) {
        final CompletionStage<GetResult<byte[]>> future = rawMemcacheClient.send(new GetRequest(encodeKey(key, charset, maxKeyLength), withCas));
        metrics.measureGetFuture(future);
        tracer.span("folsom.get", future, "get", key);
        return transformerUtil.decode(future);
    }

    @Override
    public CompletionStage<List<V>> get(final List<String> keys) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<List<GetResult<V>>> casGet(final List<String> keys) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> touch(String key, int ttl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> flushAll(final int delay) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private CompletionStage<List<GetResult<V>>> multiget(List<byte[]> keys, boolean withCas) {
        final int size = keys.size();
        if (size == 0) {
            return CompletableFuture.completedFuture(Collections.<GetResult<V>>emptyList());
        }
        final List<List<byte[]>> keyPartition = Lists.partition(keys, MemcacheEncoder.MAX_MULTIGET_SIZE);
        final List<CompletionStage<List<GetResult<byte[]>>>> futureList = new ArrayList<>(keyPartition.size());
        for (final List<byte[]> part : keyPartition) {
            MultigetRequest request = MultigetRequest.create(part, withCas);
            futureList.add(rawMemcacheClient.send(request));
        }
        final CompletionStage<List<GetResult<byte[]>>> future = ((CompletionStage<List<List<GetResult<byte[]>>>>) CompletableFutures.allAsList(futureList)).thenApply(Utils.flatten());
        metrics.measureMultigetFuture(future);
        tracer.span("folsom.multiget", future, "get");
        return transformerUtil.decodeList(future);
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#shutdown()
   */
    @Override
    public void shutdown() {
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

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#isConnected()
   */
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
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public RawMemcacheClient getRawMemcacheClient() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Map<String, AsciiMemcacheClient<V>> getAllNodes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private DefaultAsciiMemcacheClient<V> withClient(final RawMemcacheClient client) {
        return new DefaultAsciiMemcacheClient<>(client, metrics, tracer, valueTranscoder, transformerUtil, charset, maxKeyLength);
    }

    @Override
    public CompletionStage<Map<String, MemcachedStats>> getStats(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
