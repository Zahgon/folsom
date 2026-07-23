/*
 * Copyright (c) 2014-2018 Spotify AB
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
package com.spotify.folsom.client.binary;

import static com.spotify.folsom.client.AbstractRequest.encodeKey;
import static com.spotify.folsom.client.AbstractRequest.encodeKeys;
import static java.util.Objects.requireNonNull;
import com.google.common.collect.Lists;
import com.spotify.folsom.BinaryMemcacheClient;
import com.spotify.folsom.ConnectionChangeListener;
import com.spotify.folsom.GetResult;
import com.spotify.folsom.MemcacheStatus;
import com.spotify.folsom.MemcachedStats;
import com.spotify.folsom.Metrics;
import com.spotify.folsom.RawMemcacheClient;
import com.spotify.folsom.Tracer;
import com.spotify.folsom.Transcoder;
import com.spotify.folsom.client.MemcacheEncoder;
import com.spotify.folsom.client.OpCode;
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
 * The default implementation of {@link com.spotify.folsom.BinaryMemcacheClient}
 *
 * @param <V> The value type for all operations
 */
public class DefaultBinaryMemcacheClient<V> implements BinaryMemcacheClient<V> {

    private final RawMemcacheClient rawMemcacheClient;

    private final Metrics metrics;

    private final Tracer tracer;

    private final Transcoder<V> valueTranscoder;

    private final TransformerUtil<V> transformerUtil;

    private final int maxKeyLength;

    private final Charset charset;

    private DefaultBinaryMemcacheClient(final RawMemcacheClient rawMemcacheClient, final Metrics metrics, final Tracer tracer, final Transcoder<V> valueTranscoder, final TransformerUtil<V> transformerUtil, final Charset charset, final int maxKeyLength) {
        this.rawMemcacheClient = rawMemcacheClient;
        this.metrics = metrics;
        this.tracer = tracer;
        this.valueTranscoder = valueTranscoder;
        this.charset = charset;
        this.transformerUtil = transformerUtil;
        this.maxKeyLength = maxKeyLength;
    }

    public DefaultBinaryMemcacheClient(final RawMemcacheClient rawMemcacheClient, final Metrics metrics, final Tracer tracer, final Transcoder<V> valueTranscoder, final Charset charset, final int maxKeyLength) {
        this(rawMemcacheClient, metrics, tracer, valueTranscoder, new TransformerUtil<>(valueTranscoder), charset, maxKeyLength);
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#set(java.lang.String, V, int)
   */
    @Override
    public CompletionStage<MemcacheStatus> set(final String key, final V value, final int ttl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#set(java.lang.String, V, int, int)
   */
    @Override
    public CompletionStage<MemcacheStatus> set(String key, V value, int ttl, int flags) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#set(java.lang.String, V, int, long)
   */
    @Override
    public CompletionStage<MemcacheStatus> set(final String key, final V value, final int ttl, final long cas) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> set(String key, V value, int ttl, long cas, int flags) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#add(java.lang.String, V, int)
   */
    @Override
    public CompletionStage<MemcacheStatus> add(final String key, final V value, final int ttl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#add(java.lang.String, V, int, int)
   */
    @Override
    public CompletionStage<MemcacheStatus> add(String key, V value, int ttl, int flags) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#replace(java.lang.String, V, int)
   */
    @Override
    public CompletionStage<MemcacheStatus> replace(final String key, final V value, final int ttl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> replace(String key, V value, int ttl, int flags) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private CompletionStage<MemcacheStatus> setInternal(final OpCode opcode, final String key, final V value, final int ttl, final int flags) {
        return casSetInternal(opcode, key, value, ttl, 0, flags);
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#add(java.lang.String, V, int, long)
   */
    @Override
    public CompletionStage<MemcacheStatus> add(final String key, final V value, final int ttl, final long cas) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#add(java.lang.String, V, int, long, int)
   */
    @Override
    public CompletionStage<MemcacheStatus> add(String key, V value, int ttl, long cas, int flags) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#replace(java.lang.String, V, int, long)
   */
    @Override
    public CompletionStage<MemcacheStatus> replace(final String key, final V value, final int ttl, final long cas) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#replace(java.lang.String, V, int, long, int)
   */
    @Override
    public CompletionStage<MemcacheStatus> replace(String key, V value, int ttl, long cas, int flags) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private CompletionStage<MemcacheStatus> casSetInternal(final OpCode opcode, final String key, final V value, final int ttl, final long cas, final int flags) {
        requireNonNull(value);
        final byte[] valueBytes = valueTranscoder.encode(value);
        SetRequest request = new SetRequest(opcode, encodeKey(key, charset, maxKeyLength), valueBytes, ttl, cas, flags);
        CompletionStage<MemcacheStatus> future = rawMemcacheClient.send(request);
        metrics.measureSetFuture(future);
        trace(opcode, key, valueBytes, future);
        return future;
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#get(java.lang.String)
   */
    @Override
    public CompletionStage<V> get(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#getAndTouch(java.lang.String, int)
   */
    @Override
    public CompletionStage<V> getAndTouch(final String key, final int ttl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#get(java.util.List)
   */
    @Override
    public CompletionStage<List<V>> get(final List<String> keys) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#casGet(java.lang.String)
   */
    @Override
    public CompletionStage<GetResult<V>> casGet(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private CompletionStage<GetResult<V>> getInternal(final String key, final int ttl) {
        final OpCode opCode = ttl > -1 ? OpCode.GAT : OpCode.GET;
        GetRequest request = new GetRequest(encodeKey(key, charset, maxKeyLength), opCode, ttl);
        final CompletionStage<GetResult<byte[]>> future = rawMemcacheClient.send(request);
        metrics.measureGetFuture(future);
        trace(opCode, key, null, future);
        return transformerUtil.decode(future);
    }

    @Override
    public CompletionStage<List<GetResult<V>>> casGet(List<String> keys) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private CompletionStage<List<GetResult<V>>> multiget(List<byte[]> keys, int ttl) {
        final int size = keys.size();
        if (size == 0) {
            return CompletableFuture.completedFuture(Collections.<GetResult<V>>emptyList());
        }
        final List<List<byte[]>> keyPartition = Lists.partition(keys, MemcacheEncoder.MAX_MULTIGET_SIZE);
        final List<CompletionStage<List<GetResult<byte[]>>>> futureList = new ArrayList<>(keyPartition.size());
        for (final List<byte[]> part : keyPartition) {
            MultigetRequest request = MultigetRequest.create(part, ttl);
            futureList.add(rawMemcacheClient.send(request));
        }
        final CompletionStage<List<GetResult<byte[]>>> future = CompletableFutures.allAsList(futureList).thenApply(Utils.flatten());
        metrics.measureMultigetFuture(future);
        trace(OpCode.GET, null, null, future);
        return transformerUtil.decodeList(future);
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#getAndTouch(java.util.List, int)
   */
    @Override
    public CompletionStage<List<V>> getAndTouch(final List<String> keys, final int ttl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#casGetAndTouch(java.lang.String, int)
   */
    @Override
    public CompletionStage<GetResult<V>> casGetAndTouch(final String key, final int ttl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#touch(java.lang.String, int)
   */
    @Override
    public CompletionStage<MemcacheStatus> touch(final String key, final int ttl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> flushAll(final int delay) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#delete(java.lang.String)
   */
    @Override
    public CompletionStage<MemcacheStatus> delete(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CompletionStage<MemcacheStatus> delete(String key, long cas) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#deleteAll(java.lang.String)
   */
    @Override
    public CompletionStage<MemcacheStatus> deleteAll(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#incr(java.lang.String, long, long, int)
   */
    @Override
    public CompletionStage<Long> incr(final String key, final long by, final long initial, final int ttl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private CompletionStage<Long> incrInternal(final OpCode opcode, final String key, final long by, final long initial, final int ttl) {
        final CompletionStage<Long> future = rawMemcacheClient.send(new IncrRequest(encodeKey(key, charset, maxKeyLength), opcode, by, initial, ttl));
        metrics.measureIncrDecrFuture(future);
        trace(opcode, key, null, future);
        return future;
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#decr(java.lang.String, long, long, int)
   */
    @Override
    public CompletionStage<Long> decr(final String key, final long by, final long initial, final int ttl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#append(java.lang.String, V)
   */
    @Override
    public CompletionStage<MemcacheStatus> append(final String key, final V value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#append(java.lang.String, V, long)
   */
    @Override
    public CompletionStage<MemcacheStatus> append(final String key, final V value, final long cas) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#prepend(java.lang.String, V)
   */
    @Override
    public CompletionStage<MemcacheStatus> prepend(final String key, final V value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#prepend(java.lang.String, V, long)
   */
    @Override
    public CompletionStage<MemcacheStatus> prepend(final String key, final V value, final long cas) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * @see com.spotify.folsom.BinaryMemcacheClient#noop()
   */
    @Override
    public CompletionStage<Void> noop() {
        throw new UnsupportedOperationException("STUB: not implemented");
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
    public RawMemcacheClient getRawMemcacheClient() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Map<String, BinaryMemcacheClient<V>> getAllNodes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private DefaultBinaryMemcacheClient<V> withClient(final RawMemcacheClient client) {
        return new DefaultBinaryMemcacheClient<>(client, metrics, tracer, valueTranscoder, transformerUtil, charset, maxKeyLength);
    }

    @Override
    public CompletionStage<Map<String, MemcachedStats>> getStats(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void trace(final OpCode opcode, final String key, final byte[] value, final CompletionStage<?> future) {
        final String operationName = opcode.name().toLowerCase();
        final String spanName = "folsom." + operationName;
        if (key != null) {
            if (value != null) {
                tracer.span(spanName, future, operationName, key, value);
            } else {
                tracer.span(spanName, future, operationName, key);
            }
        } else {
            tracer.span(spanName, future, operationName);
        }
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
