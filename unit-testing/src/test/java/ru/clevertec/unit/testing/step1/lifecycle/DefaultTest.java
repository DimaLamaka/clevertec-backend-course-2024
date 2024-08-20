package ru.clevertec.unit.testing.step1.lifecycle;

import org.junit.jupiter.api.*;


//@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class DefaultTest {
    {
        System.out.println("initialize block");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("BeforeAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("BeforeEach");
    }

    @Test
    void test1() {
        System.out.println("test1");
        Assertions.assertTrue(true);
    }

    @Test
    void test2() {
        System.out.println("test2");
        Assertions.assertTrue(true);
    }

    @AfterEach
    void afterEach() {
        System.out.println("AfterEach");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("AfterAll");
    }
}