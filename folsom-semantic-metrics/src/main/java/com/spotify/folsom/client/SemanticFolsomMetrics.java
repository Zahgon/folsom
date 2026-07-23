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

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Meter;
import com.codahale.metrics.RatioGauge;
import com.codahale.metrics.Timer;
import com.spotify.folsom.GetResult;
import com.spotify.folsom.MemcacheStatus;
import com.spotify.folsom.Metrics;
import com.spotify.metrics.core.MetricId;
import com.spotify.metrics.core.SemanticMetricRegistry;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * {@link com.spotify.folsom.Metrics} implementation using semantic-metrics. Mostly a port of the
 * YammerMetrics class.
 */
public class SemanticFolsomMetrics implements Metrics {

    private final Timer gets;

    private final Meter getHits;

    private final Meter getMisses;

    private final Meter getFailures;

    /**
     * reports the ratio of hits for (gets + multigets) to total (gets + multigets)
     */
    private final RatioGauge hitRatio;

    private final Timer sets;

    private final Meter setSuccesses;

    private final Meter setFailures;

    private final Timer multigets;

    private final Meter multigetSuccesses;

    private final Meter multigetFailures;

    private final Timer deletes;

    private final Meter deleteSuccesses;

    private final Meter deleteFailures;

    private final Timer incrDecrs;

    private final Meter incrDecrSuccesses;

    private final Meter incrDecrFailures;

    private final Timer touches;

    private final Meter touchSuccesses;

    private final Meter touchFailures;

    private final SemanticMetricRegistry registry;

    private final MetricId id;

    private final Set<OutstandingRequestsGauge> gauges = new CopyOnWriteArraySet<>();

    public SemanticFolsomMetrics(final SemanticMetricRegistry registry, final MetricId baseMetricId) {
        this.registry = registry;
        this.id = baseMetricId.tagged("what", "memcache-results", "component", "memcache-client");
        final MetricId meterId = id.tagged("unit", "operations");
        MetricId getId = id.tagged("operation", "get");
        this.gets = registry.timer(getId);
        // successful gets are broken down by whether a result was found in the cache or not.
        // the two meters can be summed to count total number of successes.
        MetricId getMetersId = MetricId.join(getId, meterId);
        this.getHits = registry.meter(getMetersId.tagged("result", "success", "cache-result", "hit"));
        this.getMisses = registry.meter(getMetersId.tagged("result", "success", "cache-result", "miss"));
        this.getFailures = registry.meter(getMetersId.tagged("result", "failure"));
        // ratio of cache hits to total attempts
        hitRatio = new RatioGauge() {

            @Override
            protected Ratio getRatio() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        };
        // overwrite the 'what' as this metric doesn't make sense to be aggregated against any of the
        // other metrics
        registry.register(getId.tagged("what", "memcache-hit-ratio", "unit", "%"), hitRatio);
        MetricId setId = id.tagged("operation", "set");
        this.sets = registry.timer(setId);
        MetricId setMetersId = MetricId.join(setId, meterId);
        this.setSuccesses = registry.meter(setMetersId.tagged("result", "success"));
        this.setFailures = registry.meter(setMetersId.tagged("result", "failure"));
        MetricId multigetId = id.tagged("operation", "multiget");
        this.multigets = registry.timer(multigetId);
        MetricId multigetMetersId = MetricId.join(multigetId, meterId);
        this.multigetSuccesses = registry.meter(multigetMetersId.tagged("result", "success"));
        this.multigetFailures = registry.meter(multigetMetersId.tagged("result", "failure"));
        MetricId deleteId = id.tagged("operation", "delete");
        this.deletes = registry.timer(deleteId);
        MetricId deleteMetersId = MetricId.join(deleteId, meterId);
        this.deleteSuccesses = registry.meter(deleteMetersId.tagged("result", "success"));
        this.deleteFailures = registry.meter(deleteMetersId.tagged("result", "failure"));
        MetricId incrDecrId = id.tagged("operation", "incr-decr");
        this.incrDecrs = registry.timer(incrDecrId);
        MetricId incrDecrMetersId = MetricId.join(incrDecrId, meterId);
        this.incrDecrSuccesses = registry.meter(incrDecrMetersId.tagged("result", "success"));
        this.incrDecrFailures = registry.meter(incrDecrMetersId.tagged("result", "failure"));
        MetricId touchId = id.tagged("operation", "touch");
        this.touches = registry.timer(touchId);
        MetricId touchMetersId = MetricId.join(touchId, meterId);
        this.touchSuccesses = registry.meter(touchMetersId.tagged("result", "success"));
        this.touchFailures = registry.meter(touchMetersId.tagged("result", "failure"));
        final MetricId outstandingRequestGauge = id.tagged("what", "outstanding-requests", "unit", "requests");
        registry.register(outstandingRequestGauge, (Gauge<Long>) () -> gauges.stream().mapToLong(OutstandingRequestsGauge::getOutstandingRequests).sum());
        final MetricId globalConnectionCountGauge = id.tagged("what", "global-connections", "unit", "connections");
        registry.register(globalConnectionCountGauge, (Gauge<Integer>) Utils::getGlobalConnectionCount);
    }

    @Override
    public void measureGetFuture(CompletionStage<GetResult<byte[]>> future) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void measureSetFuture(CompletionStage<MemcacheStatus> future) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void measureMultigetFuture(CompletionStage<List<GetResult<byte[]>>> future) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void measureDeleteFuture(CompletionStage<MemcacheStatus> future) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void measureIncrDecrFuture(CompletionStage<Long> future) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void measureTouchFuture(CompletionStage<MemcacheStatus> future) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Timer getGets() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getGetHits() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getGetMisses() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getGetFailures() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Timer getSets() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getSetSuccesses() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getSetFailures() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Timer getMultigets() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getMultigetSuccesses() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getMultigetFailures() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Timer getDeletes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getDeleteSuccesses() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getDeleteFailures() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Timer getIncrDecrs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getIncrDecrSuccesses() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getIncrDecrFailures() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Timer getTouches() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getTouchSuccesses() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getTouchFailures() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public RatioGauge getHitRatio() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void registerOutstandingRequestsGauge(final OutstandingRequestsGauge gauge) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void unregisterOutstandingRequestsGauge(OutstandingRequestsGauge gauge) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
