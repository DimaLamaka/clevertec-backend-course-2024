package ru.clevertec.application.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class GsonConfiguration {

    @Bean
    public GsonBuilderCustomizer gsonBuilderCustomizer() {
        return gsonBuilder -> gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
    }

    @Bean
    public MyBean myBean() {
        return new MyBean("name");
    }

    @Data
    @AllArgsConstructor
    public static class MyBean {
        private String name;
    }

    private static class LocalDateAdapter implements JsonSerializer<LocalDate> {

        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }

}
