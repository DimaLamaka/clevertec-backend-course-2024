package ru.clevertec.unit.testing.step3.depenencyinjection;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.unit.testing.model.User;
import ru.clevertec.unit.testing.util.UuidUtil;

import java.util.UUID;

import static ru.clevertec.unit.testing.model.User.Role.GUEST;
import static ru.clevertec.unit.testing.model.User.Role.USER;

@ExtendWith(
        {
                InjectUserResolverExtension.class,
                InjectUserPostProcessor.class
        })
class DependencyInjectionTest {

    @InjectUser(name = "vasya")
    User defaultUser;

    //TestInfo - display name, tags, class and method info
    //Repetitions info - repeated test info

    @Test
    void testPostProcessorInjectUser() {
        Assertions.assertNotNull(defaultUser);
        Assertions.assertEquals("vasya", defaultUser.getName());
        Assertions.assertEquals(USER, defaultUser.getRole());
    }

    @Test
    void testPropertyResolverUser(@InjectUser(name = "kolya") User user) {
        Assertions.assertNotNull(user);
        Assertions.assertEquals("kolya", user.getName());
        Assertions.assertEquals(GUEST, user.getRole());
    }

    @Test
    @Tag("new")
    void testGenerateUuidTestInfo(TestInfo testInfo) {
        System.out.println("testGenerateUuidTestInfo " + testInfo.getTestMethod());
//        System.out.println("Random value is: " + value);
        UUID uuid = UuidUtil.generateUuid();

        Assertions.assertNotNull(uuid);
    }

    @RepeatedTest(2)
    @Tag("new")
    void testGenerateRepetitionInfo(RepetitionInfo repetitionInfo) {
        System.out.println("testGenerateRepetitionInfo " + this);
        UUID uuid = UuidUtil.generateUuid();

        Assertions.assertNotNull(uuid);
    }

    @Test
    @DisplayName("Test Reporter")
    @Tag("new")
//    @Disabled("Test reporter not working")
    void testGenerateTestReporter(TestReporter testReporter) {
        System.out.println("testGenerateTestReporter " + this);
        UUID uuid = UuidUtil.generateUuid();
        testReporter.publishEntry("KEY", uuid.toString());
    }

}
