package ru.clevertec.application.caching;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import ru.clevertec.application.IT;
import ru.clevertec.application.configuration.GsonConfiguration.MyBean;
import ru.clevertec.application.configuration.PostgresContainerConfiguration;

@IT
@DirtiesContext
@ContextConfiguration(classes = PostgresContainerConfiguration.class)
class CachingBadContextFirstTest {
    @Autowired
    private MyBean myBean;

    @Test
    void test() {
        myBean.setName("naaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaameeeeeeeeeeeeeee");
    }
}
