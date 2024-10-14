package ru.clevertec.application.mockmvc;

import com.google.gson.Gson;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockMvcTestConfiguration {
    @Bean
    public Gson gson() {
        return new Gson();
    }
}
