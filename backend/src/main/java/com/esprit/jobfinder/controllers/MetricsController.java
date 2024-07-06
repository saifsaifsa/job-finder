package com.esprit.jobfinder.controllers;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/actuator")
public class MetricsController {
    private final Counter customCounter;

    public MetricsController(MeterRegistry meterRegistry) {
        this.customCounter = meterRegistry.counter("custom.metrics.counter");
    }

    @GetMapping("/metric")
    public double getCustomMetric() {
        return customCounter.count();
    }
}
