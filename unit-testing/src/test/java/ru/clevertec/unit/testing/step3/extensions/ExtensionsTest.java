package ru.clevertec.unit.testing.step3.extensions;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.unit.testing.util.UuidUtil;

import java.util.UUID;

@ExtendWith(
        {
//                TestInstancePostProcessorExtension.class,
//                SystemPropertyExecutionCondition.class,
                CallbackExtension.class
//                ParameterResolverExtension.class,
//                NumberFormatExceptionHandlerExecution.class
        })
@Tag("DEV")
class ExtensionsTest {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Before each");
    }

    @Test
    @Tag("new")
    void testGenerateUuidTestInfo(@CustomRandom Integer value) {
        System.out.println("testGenerateUuidTestInfo " + this);
        System.out.println("Random value is: " + value);
        UUID uuid = UuidUtil.generateUuid();

        Assertions.assertNotNull(uuid);
    }

    @Test
    void testGenerateRepetitionInfo() {
        System.out.println("testGenerateRepetitionInfo " + this);
        UUID uuid = UuidUtil.generateUuid();

        Assertions.assertNotNull(uuid);
    }

    @Test
    void testIgnoreException() {
        long value = Long.parseLong("value");
    }

    @AfterEach
    void afterEach() {
        System.out.println("After each");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After all");
    }
}
