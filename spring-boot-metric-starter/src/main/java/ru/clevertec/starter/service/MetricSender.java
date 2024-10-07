package ru.clevertec.starter.service;

import java.util.Map;

public interface MetricSender {

    void sendMetrics(Map<String, String> metrics);
}
