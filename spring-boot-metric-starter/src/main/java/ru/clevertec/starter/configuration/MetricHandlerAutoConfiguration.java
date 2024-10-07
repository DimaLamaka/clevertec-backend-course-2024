package ru.clevertec.starter.configuration;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.scheduling.annotation.EnableAsync;
import ru.clevertec.starter.bpp.MetricHandlerBeanPostProcessor;
import ru.clevertec.starter.condition.ExcludeProfileConditional;
import ru.clevertec.starter.listener.ApplicationStartListener;
import ru.clevertec.starter.properties.MetricSenderProperties;
import ru.clevertec.starter.service.LoggerMetricSender;
import ru.clevertec.starter.service.MetricSender;
import ru.clevertec.starter.service.MetricSenderHandler;

@AutoConfiguration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@EnableAsync
@EnableConfigurationProperties(MetricSenderProperties.class)
@ConditionalOnProperty(value = "metric.sender.enable", havingValue = "true")
@ExcludeProfileConditional(excludeProfiles = {"production", "prelive"})
public class MetricHandlerAutoConfiguration {

    @Bean
    public MetricHandlerBeanPostProcessor metricHandlerBeanPostProcessor() {
        return new MetricHandlerBeanPostProcessor();
    }

    @Bean
    public MetricSenderHandler metricSenderHandler() {
        return new MetricSenderHandler();
    }

    @Bean
    public MetricSender loggerMetricSender() {
        return new LoggerMetricSender();
    }

    @Bean
    public ApplicationStartListener applicationStartListener(MetricSenderProperties metricSenderProperties) {
        return new ApplicationStartListener(metricSenderProperties);
    }
}
