package ru.clevertec.starter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class MetricSenderHandler {

    @Async
    public void sendMetricsAsync(Map<String, String> metrics, Set< ? extends MetricSender> finalMetricSenders) {
        finalMetricSenders
                .forEach(metricSender -> metricSender.sendMetrics(metrics));
    }
}
