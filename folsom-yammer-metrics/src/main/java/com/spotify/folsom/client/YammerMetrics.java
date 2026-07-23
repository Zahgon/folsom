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

import static java.util.concurrent.TimeUnit.SECONDS;
import com.google.common.annotations.VisibleForTesting;
import com.spotify.folsom.GetResult;
import com.spotify.folsom.MemcacheStatus;
import com.spotify.folsom.Metrics;
import com.yammer.metrics.core.Gauge;
import com.yammer.metrics.core.Meter;
import com.yammer.metrics.core.MetricName;
import com.yammer.metrics.core.MetricsRegistry;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.TimerContext;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArraySet;

public class YammerMetrics implements Metrics {

    public static final String GROUP = "com.spotify.folsom";

    private final Timer gets;

    private final Meter getHits;

    private final Meter getMisses;

    private final Meter getSuccesses;

    private final Meter getFailures;

    private final Timer multigets;

    private final Meter multigetSuccesses;

    private final Meter multigetFailures;

    private final Timer sets;

    private final Meter setSuccesses;

    private final Meter setFailures;

    private final Timer deletes;

    private final Meter deleteSuccesses;

    private final Meter deleteFailures;

    private final Timer incrDecrs;

    private final Meter incrDecrSuccesses;

    private final Meter incrDecrFailures;

    private final Timer touches;

    private final Meter touchSuccesses;

    private final Meter touchFailures;

    private final Set<OutstandingRequestsGauge> gauges = new CopyOnWriteArraySet<>();

    public YammerMetrics(final MetricsRegistry registry) {
        this.gets = registry.newTimer(name("get", "requests"), SECONDS, SECONDS);
        this.getSuccesses = registry.newMeter(name("get", "successes"), "Successes", SECONDS);
        this.getHits = registry.newMeter(name("get", "hits"), "Hits", SECONDS);
        this.getMisses = registry.newMeter(name("get", "misses"), "Misses", SECONDS);
        this.getFailures = registry.newMeter(name("get", "failures"), "Failures", SECONDS);
        this.multigets = registry.newTimer(name("multiget", "requests"), SECONDS, SECONDS);
        this.multigetSuccesses = registry.newMeter(name("multiget", "successes"), "Successes", SECONDS);
        this.multigetFailures = registry.newMeter(name("multiget", "failures"), "Failures", SECONDS);
        this.sets = registry.newTimer(name("set", "requests"), SECONDS, SECONDS);
        this.setSuccesses = registry.newMeter(name("set", "successes"), "Successes", SECONDS);
        this.setFailures = registry.newMeter(name("set", "failures"), "Failures", SECONDS);
        this.deletes = registry.newTimer(name("delete", "requests"), SECONDS, SECONDS);
        this.deleteSuccesses = registry.newMeter(name("delete", "successes"), "Successes", SECONDS);
        this.deleteFailures = registry.newMeter(name("delete", "failures"), "Failures", SECONDS);
        this.incrDecrs = registry.newTimer(name("incrdecr", "requests"), SECONDS, SECONDS);
        this.incrDecrSuccesses = registry.newMeter(name("incrdecr", "successes"), "Successes", SECONDS);
        this.incrDecrFailures = registry.newMeter(name("incrdecr", "failures"), "Failures", SECONDS);
        this.touches = registry.newTimer(name("touch", "requests"), SECONDS, SECONDS);
        this.touchSuccesses = registry.newMeter(name("touch", "successes"), "Successes", SECONDS);
        this.touchFailures = registry.newMeter(name("touch", "failures"), "Failures", SECONDS);
        final MetricName gaugeName = name("outstandingRequests", "count");
        registry.newGauge(gaugeName, new Gauge<Long>() {

            @Override
            public Long value() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
        final MetricName globalConnections = name("global-connections", "count");
        registry.newGauge(globalConnections, new Gauge<Integer>() {

            @Override
            public Integer value() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
    }

    @VisibleForTesting
    long getOutstandingRequests() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private MetricName name(final String type, final String name) {
        return new MetricName(GROUP, type, name);
    }

    @Override
    public void measureGetFuture(CompletionStage<GetResult<byte[]>> future) {
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
    public void measureSetFuture(CompletionStage<MemcacheStatus> future) {
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

    @Override
    public void registerOutstandingRequestsGauge(OutstandingRequestsGauge gauge) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void unregisterOutstandingRequestsGauge(OutstandingRequestsGauge gauge) {
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

    public Meter getGetSuccesses() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getGetFailures() {
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

    public Timer getSets() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getSetSuccesses() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Meter getSetFailures() {
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
}
