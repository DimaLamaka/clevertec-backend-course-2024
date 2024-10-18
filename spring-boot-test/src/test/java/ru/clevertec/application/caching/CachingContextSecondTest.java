package ru.clevertec.application.caching;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import ru.clevertec.application.IT;
import ru.clevertec.application.configuration.PostgresContainerConfiguration;

@IT
@ContextConfiguration(classes = PostgresContainerConfiguration.class)
class CachingContextSecondTest {
    @SpyBean
//    @Autowired
    private Gson gson;

    @Test
    void test(){
        gson.toJson("new");
    }
}
