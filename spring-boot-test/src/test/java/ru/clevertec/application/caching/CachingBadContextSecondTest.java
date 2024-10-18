package ru.clevertec.application.caching;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import ru.clevertec.application.IT;
import ru.clevertec.application.configuration.GsonConfiguration.MyBean;
import ru.clevertec.application.configuration.PostgresContainerConfiguration;

@IT
@ContextConfiguration(classes = PostgresContainerConfiguration.class)
class CachingBadContextSecondTest {
    @Autowired
    private MyBean myBean;

    @Test
    void test() {
        Assertions.assertThat(myBean)
                .extracting(MyBean::getName)
                .isEqualTo("name");
    }
}
