package com.esprit.jobfinder.aspects;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;
public class Metrics {
    public Metrics(MeterRegistry meterRegistry) {
        meterRegistry.counter("custom.metrics.counter").increment();
        meterRegistry.gauge("custom.metrics.gauge", 1);
    }
}
