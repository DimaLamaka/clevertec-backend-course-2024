package ru.clevertec.starter.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.clevertec.starter.enums.Metric;
import ru.clevertec.starter.service.MetricSender;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Data
@ConfigurationProperties(prefix = "metric.sender.default")
public class MetricSenderProperties {
    private final Set<Metric> defaultMetrics = new HashSet<>();
    private final Set<Class<? extends MetricSender>> defaultMetricSenders = new HashSet<>();
}
