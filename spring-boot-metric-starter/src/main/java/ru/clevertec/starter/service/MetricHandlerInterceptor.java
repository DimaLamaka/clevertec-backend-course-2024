package ru.clevertec.starter.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import ru.clevertec.starter.annotation.MetricHandler;
import ru.clevertec.starter.enums.Metric;
import ru.clevertec.starter.properties.MetricSenderProperties;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static ru.clevertec.starter.enums.Metric.*;

@Slf4j
@AllArgsConstructor
public class MetricHandlerInterceptor implements MethodInterceptor {
    private static final String UNDERSCORE_DELIMITER = "_";
    private static final String ONE = "1";

    private final Object originalBean;
    private final MetricSenderHandler metricSenderHandler;
    private final MetricSenderProperties metricSenderProperties;
    private final BeanFactory beanFactory;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (method.isAnnotationPresent(MetricHandler.class)) {
            MetricHandler annotation = method.getAnnotation(MetricHandler.class);
            Set<Metric> finalMetrics = getFinalMetrics(annotation.metrics(), annotation.includeDefaultMetrics());
            Set<? extends MetricSender> finalMetricSenders = getFinalMetricSenders(annotation.metricSenders(), annotation.includeDefaultMetricSender())
                    .stream()
                    .map(beanFactory::getBean)
                    .collect(Collectors.toSet());

            Map<String, String> metrics = new HashMap<>();

            long startExecution = System.nanoTime();
            long endExecution = System.nanoTime();

            try {
                Object invoke = method.invoke(originalBean, args);
                endExecution = System.nanoTime();

                if (finalMetrics.contains(SUCCESS_COUNTER)) {
                    metrics.put(getMetricName(annotation.metricPrefixName(), SUCCESS_COUNTER.name(), UNDERSCORE_DELIMITER), ONE);
                }
                return invoke;
            } catch (Exception exception) {
                endExecution = System.nanoTime();

                if (finalMetrics.contains(FAIL_COUNTER)) {
                    metrics.put(getMetricName(annotation.metricPrefixName(), FAIL_COUNTER.name(), UNDERSCORE_DELIMITER), ONE);
                }
                throw exception;
            } finally {
                if (finalMetrics.contains(EXECUTION_TIME)) {
                    metrics.put(getMetricName(annotation.metricPrefixName(), EXECUTION_TIME.name(), UNDERSCORE_DELIMITER), String.valueOf(TimeUnit.NANOSECONDS.toMillis(endExecution - startExecution)));
                }
                if (!metrics.isEmpty()) {
                    metricSenderHandler.sendMetricsAsync(metrics, finalMetricSenders);
                }
            }
        }
        return method.invoke(originalBean, args);
    }

    private Set<Metric> getFinalMetrics(Metric[] metrics, boolean includeDefaultMetrics) {
        Set<Metric> finalMetrics = Arrays.stream(metrics).collect(Collectors.toSet());
        if (includeDefaultMetrics) {
            finalMetrics.addAll(metricSenderProperties.getDefaultMetrics());
        }
        return finalMetrics;
    }

    private Set<Class<? extends MetricSender>> getFinalMetricSenders(Class<? extends MetricSender>[] metricSenders,
                                                                     boolean includeDefaultMetricSender) {
        Set<Class<? extends MetricSender>> finalMetricSenders = Arrays.stream(metricSenders).collect(Collectors.toSet());
        if (includeDefaultMetricSender) {
            finalMetricSenders.addAll(metricSenderProperties.getDefaultMetricSenders());
        }
        return finalMetricSenders;
    }

    private String getMetricName(String prefix, String metricName, String delimiter) {
        return prefix.concat(delimiter).concat(metricName).toLowerCase();
    }
}
