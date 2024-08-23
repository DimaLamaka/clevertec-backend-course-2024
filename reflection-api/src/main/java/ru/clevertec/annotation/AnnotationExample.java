package ru.clevertec.annotation;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;

public class AnnotationExample {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        ValueHolder valueHolder = new ValueHolder();
        System.out.println("value is: %s".formatted(valueHolder.getValue()));

        Arrays.stream(valueHolder.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(RandomValue.class))
                .forEach(field -> setRandomValueToField(field, valueHolder));

        System.out.println("after handle value is: %s".formatted(valueHolder.getValue()));
    }

    private static void setRandomValueToField(Field field, Object object) {
        RandomValue annotation = field.getAnnotation(RandomValue.class);
        int value = RANDOM.nextInt(annotation.max() - annotation.min()) + annotation.min();
        try {
            if (field.getType().equals(Integer.TYPE)) {
                field.setAccessible(true);
                field.setInt(object, value);
                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
