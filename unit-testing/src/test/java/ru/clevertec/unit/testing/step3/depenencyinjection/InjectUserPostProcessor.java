package ru.clevertec.unit.testing.step3.depenencyinjection;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import ru.clevertec.unit.testing.model.User;
import ru.clevertec.unit.testing.util.UuidUtil;

import java.lang.reflect.Field;
import java.util.Arrays;

public class InjectUserPostProcessor implements TestInstancePostProcessor {
    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        Arrays.stream(testInstance.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(InjectUser.class))
                .filter(field -> field.getType().equals(User.class))
                .forEach(field -> extractUserInfoAndInject(testInstance, field));
    }

    private static void extractUserInfoAndInject(Object testInstance, Field field) {
        InjectUser annotation = field.getAnnotation(InjectUser.class);
        String name = annotation.name();
        User user = new User()
                .setUuid(UuidUtil.generateUuid())
                .setName(name)
                .setRole(User.Role.USER);
        if (field.trySetAccessible()) {
            try {
                field.set(testInstance, user);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
