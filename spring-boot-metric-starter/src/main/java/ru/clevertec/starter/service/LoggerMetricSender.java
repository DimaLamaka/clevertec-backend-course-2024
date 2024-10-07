package ru.clevertec.starter.service;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class LoggerMetricSender implements MetricSender {
    @Override
    public void sendMetrics(Map<String, String> metrics) {
        metrics.forEach((key, value) -> log.info("Metric: {}, value: {}", key, value));
    }
}
