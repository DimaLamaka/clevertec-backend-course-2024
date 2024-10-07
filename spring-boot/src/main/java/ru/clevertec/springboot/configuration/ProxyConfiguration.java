package ru.clevertec.springboot.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProxyConfiguration {

    @Bean
    public NewBean newBeanZZZ() {
        NewBean newBean = new NewBean("25");
        log.info("ZZZ = {}", System.identityHashCode(newBean));
        return newBean;
    }

    @Bean
    public NewBean newBeanAAA() {
        NewBean newBean = newBeanZZZ();
        log.info("AAA bean zzz = {}", System.identityHashCode(newBean));
        return newBean;
    }

    @Bean
    public NewBean newBeanBBB() {
        NewBean newBean = newBeanZZZ();
        log.info("BBB bean zzz = {}", System.identityHashCode(newBean));
        return newBean;
    }
}
