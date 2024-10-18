package ru.clevertec.application.container;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestContainersPostgres {
    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("OfferDB")
            .withUsername("user")
            .withPassword("pass");

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
        setPostgresProperties();
    }

    //    @DynamicPropertySource
    static void setPostgresProperties() {
        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
//        registry.add();
//        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
//        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
//        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }
}
