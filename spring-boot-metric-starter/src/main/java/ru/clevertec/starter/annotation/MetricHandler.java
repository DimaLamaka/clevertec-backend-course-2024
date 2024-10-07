package ru.clevertec.starter.annotation;

import ru.clevertec.starter.enums.Metric;
import ru.clevertec.starter.service.MetricSender;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetricHandler {
    String metricPrefixName();

    Metric[] metrics() default {};

    boolean includeDefaultMetricSender() default true;

    boolean includeDefaultMetrics() default true;

    Class<? extends MetricSender>[] metricSenders() default {};
}
