package ru.clevertec.unit.testing.step2.annotations.dynamic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

interface DynamicInterfaceTest {

    @TestFactory
    default Stream<DynamicTest> dynamicTests() {
        return Stream.of(10, 20, 30)
                .map(number -> DynamicTest.dynamicTest("display name " + number, () -> Assertions.assertEquals(0, number % 10)));
    }
}
