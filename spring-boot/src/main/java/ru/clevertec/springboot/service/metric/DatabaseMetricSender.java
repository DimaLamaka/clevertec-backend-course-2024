package ru.clevertec.springboot.service.metric;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.springboot.entity.Metric;
import ru.clevertec.springboot.repository.MetricRepository;
import ru.clevertec.starter.service.MetricSender;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseMetricSender implements MetricSender {
    private final MetricRepository metricRepository;

    @Transactional
    @Override
    public void sendMetrics(Map<String, String> map) {
        log.info("Push metrics to h2 repository");
        List<Metric> metrics = map.entrySet().stream()
                .map(metric -> new Metric()
                        .setName(metric.getKey())
                        .setValue(metric.getValue()))
                .toList();
        metricRepository.saveAll(metrics);
    }
}
