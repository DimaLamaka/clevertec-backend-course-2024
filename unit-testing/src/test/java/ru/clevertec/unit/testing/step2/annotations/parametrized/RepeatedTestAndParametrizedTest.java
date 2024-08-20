package ru.clevertec.unit.testing.step2.annotations.parametrized;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.*;
import ru.clevertec.unit.testing.model.User;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static ru.clevertec.unit.testing.model.User.*;

class RepeatedTestAndParametrizedTest {

    @RepeatedTest(
            value = 10,
            name = "attempt {currentRepetition} of {totalRepetitions}"
    )
    @DisplayName("test repeated annotation")
    void testRepeated(RepetitionInfo repetitionInfo) {
        System.out.println(repetitionInfo.getCurrentRepetition() + " -> " + repetitionInfo.getTotalRepetitions());
        Assertions.assertTrue(true);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 1240, 9340, 20})
    void testValueSource(Integer value) {
        Assertions.assertTrue(value % 10 == 0);
    }

    @ParameterizedTest
//    @NullSource
//    @EmptySource
    @NullAndEmptySource
    void testNullEmptySource(Set<String> value) {
        Assertions.assertTrue(Objects.isNull(value) || value.isEmpty());
    }

    @ParameterizedTest
    @EnumSource(
            value = Role.class,
            names = {"ADMIN", "USER"},
            mode = EnumSource.Mode.EXCLUDE
    )
    void testEnumSource(Role role) {
        Assertions.assertEquals("GUEST", role.name());
    }

    @ParameterizedTest
    @MethodSource({"methodSource1", "methodSource2"})
    void testMethodSource(User user, String expected) {
        Assertions.assertEquals(expected, user.getRole().name());
    }

    static Stream<Arguments> methodSource1() {
        return Stream.of(
                Arguments.of(new User().setRole(Role.ADMIN), "ADMIN"),
                Arguments.of(new User().setRole(Role.GUEST), "GUEST"),
                Arguments.of(new User().setRole(Role.USER), "USER")
        );
    }

    static Stream<Arguments> methodSource2() {
        return Stream.of(
                Arguments.of(new User().setRole(Role.ADMIN), "ADMIN"),
                Arguments.of(new User().setRole(Role.GUEST), "GUEST"),
                Arguments.of(new User().setRole(Role.USER), "USER")
        );
    }

    @ParameterizedTest(name = "value is -> {0}")
    @MethodSource("stringProducer")
    void testPrimitiveMethodSource(String role) {
        Assertions.assertNotNull(role);
    }

    //work with IntStream, DoubleStream, LongStream
    static Stream<String> stringProducer() {
        return Stream.of("admin", "user", "guest");
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "'vasya-ADMIN', ADMIN",
                    "'petya-USER', USER",
                    "'zhenya-GUEST', GUEST"
            }
    )
//    @CsvFileSource(resources = {"{path.to.file}"})
    void testCsvMethodSource(String nameWithRole, String role) {
        Assertions.assertTrue(nameWithRole.contains(role));
    }

    @ParameterizedTest
    @ValueSource(strings = {"USER", "ADMIN", "GUEST"})
    void testDefaultConverter(Role role) {
        Assertions.assertNotNull(role);
    }

    @ParameterizedTest
    @ValueSource(strings = {"USER", "ADMIN", "GUEST"})
    void testWithArgumentAggregation(ArgumentsAccessor argumentsAccessor) {
        Assertions.assertNotNull(argumentsAccessor.getString(0));
    }
}
