package ru.clevertec.starter.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import ru.clevertec.starter.properties.MetricSenderProperties;

@Slf4j
@RequiredArgsConstructor
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {
    private final MetricSenderProperties metricSenderProperties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Default metric properties: metrics - {}, metric senders - {}",
                metricSenderProperties.getDefaultMetrics(),
                metricSenderProperties.getDefaultMetricSenders());
    }
}
