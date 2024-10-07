package ru.clevertec.starter.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import ru.clevertec.starter.annotation.MetricHandler;
import ru.clevertec.starter.properties.MetricSenderProperties;
import ru.clevertec.starter.service.MetricHandlerInterceptor;
import ru.clevertec.starter.service.MetricSenderHandler;

import java.lang.reflect.Constructor;
import java.util.*;

public class MetricHandlerBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
    private final Map<String, Class<?>> beanNamesWithAnnotatedMethods = new HashMap<>();
    private BeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        boolean annotationPresent = Arrays.stream(clazz.getMethods())
                .anyMatch(method -> method.isAnnotationPresent(MetricHandler.class));
        if (annotationPresent) {
            beanNamesWithAnnotatedMethods.put(beanName, clazz);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return Optional.ofNullable(beanNamesWithAnnotatedMethods.get(beanName))
                .map(clazz -> getMetricProxy(bean))
                .orElse(bean);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    private Object getMetricProxy(Object bean) {
        MetricSenderHandler metricSenderHandler = beanFactory.getBean(MetricSenderHandler.class);
        MetricSenderProperties metricSenderProperties = beanFactory.getBean(MetricSenderProperties.class);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(bean.getClass());
        enhancer.setCallback(new MetricHandlerInterceptor(bean, metricSenderHandler, metricSenderProperties, beanFactory));
        return isPresentDefaultConstructor(bean)
                ? enhancer.create()
                : enhancer.create(getNotDefaultConstructorArgTypes(bean), getNotDefaultConstructorArgs(bean));

    }

    private boolean isPresentDefaultConstructor(Object bean) {
        return Arrays.stream(bean.getClass().getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);
    }

    private Class<?>[] getNotDefaultConstructorArgTypes(Object object) {
        return Arrays.stream(object.getClass().getConstructors())
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .map(Constructor::getParameterTypes)
                .orElseThrow(IllegalArgumentException::new);
    }

    private Object[] getNotDefaultConstructorArgs(Object object) {
        Class<?>[] constructorArgTypes = getNotDefaultConstructorArgTypes(object);
        return Arrays.stream(constructorArgTypes)
                .map(beanFactory::getBean)
                .toArray();
    }
}
