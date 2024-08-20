package ru.clevertec.unit.testing.step2.annotations.assertsandother;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import ru.clevertec.unit.testing.util.UuidUtil;

import java.time.Duration;
import java.util.List;

@Tags({
        @Tag("annotations"),
        @Tag("assertions"),
        @Tag("assumptions")
})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AnnotationsTest {

    @Tag("tag")
    @Test
    @DisplayName("")
    void shouldSuccessReturnResponseIfConditionTrue() {
        Assertions.assertTrue(true);
    }

    @Fast
    void testWithCustomAnnotation() {
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("\uD83D\uDE31")
    void testDisplayName() {
        Assertions.assertTrue(true);
    }

    @Tag("assertions")
    @Test
    void testAsserts() {
        Assertions.assertTrue(true);

        Assertions.assertEquals("java", "java", () -> "Should be equals");
        Assertions.assertNotEquals("java", null);

        Assertions.assertNotNull("java");
        Assertions.assertNull(null);

        Assertions.assertAll(
                () -> Assertions.assertTrue(true),
                () -> Assertions.assertFalse(false)
        );

        Assertions.assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3});
        Assertions.assertIterableEquals(List.of(1, 2, 3), List.of(1, 2, 3));

        Assertions.assertInstanceOf(RuntimeException.class, new IllegalArgumentException());

        Assertions.assertLinesMatch(List.of("java", "c++"), List.of("java", "c++"));

        Assertions.assertSame("java", "java");
        Assertions.assertNotSame("c++", "c#");

        Assertions.assertThrows(NumberFormatException.class, () -> Long.parseLong("3d2"));
        Assertions.assertDoesNotThrow(UuidUtil::generateUuid);

        Assertions.assertTimeout(Duration.ofMillis(100), UuidUtil::generateUuid);
    }

    @Test
    void testAssumptions() {
        System.setProperty("profile","PROD");

        Assumptions.assumeTrue("PROD".equals(System.getProperty("profile")));
        System.out.println("After assume");
        Assertions.assertTrue(true);

        Assumptions.assumingThat(() -> "PROD".equals(System.getProperty("profile")), () -> Assertions.assertEquals(4, 2));
        Assumptions.assumingThat(() -> false, () -> Assertions.assertEquals(1, 2));
    }

    @Test
    @Disabled("reason")
    @DisabledIf("customCondition")
    @DisabledOnOs(OS.WINDOWS)
    @DisabledOnJre(JRE.JAVA_11)
    @DisabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_11)
    @DisabledIfEnvironmentVariable(named = "env", matches = "DEV")
    @DisabledIfSystemProperty(named = "test", matches = "DEV")
    void testDisabled() {
        Assertions.assertTrue(false);
    }

    private boolean customCondition(){
        return false;
    }
}

