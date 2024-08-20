package ru.clevertec.unit.testing.step2.annotations.ordernested;

import org.junit.jupiter.api.*;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
//@TestMethodOrder(MethodOrderer.DisplayName.class)
//@TestMethodOrder(MethodOrderer.Random.class)
//@Test
class MethodOrderAndNestedTest {

    @Test
    @Order(3)
    void test3() {
        Assertions.assertTrue(true);
    }

    @Order(1)
    @Test
    void test1() {
        Assertions.assertTrue(true);
    }

    @Order(2)
    @Test
    void test2() {
        Assertions.assertTrue(true);
    }

    @Order(4)
    @Test
    void test4() {
        Assertions.assertTrue(true);
    }

    @Nested
    @Tag("nested")
    @DisplayName("test nested class")
    class NestedClass {

        @Test
        void nestedTest1(){
            Assertions.assertTrue(true);
        }

        @Test
        void nestedTest2(){
            Assertions.assertTrue(true);
        }
    }
}
