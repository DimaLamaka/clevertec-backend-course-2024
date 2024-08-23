package ru.clevertec.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class ReflectionFieldExample {

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface MyAnnotation {

    }

    static class Task {
        private final List<String> strings;
        @MyAnnotation
        private long id;
        public String title;

        public Task(long id, String title, List<String> strings) {
            this.id = id;
            this.title = title;
            this.strings = strings;
        }
    }

    public static void main(String[] args) {
        try {
            Task task = new Task(1, "title", List.of("1", "2", "3"));

            Class<? extends Task> taskClass = task.getClass();

            //get public field by name
            Field titleField = taskClass.getField("title");
            //get value field
            String value = (String) titleField.get(task);

            //get public or private field by name
            Field idField = taskClass.getDeclaredField("id");
            //get value
            Long id = (Long) idField.get(task);

            //get all public fields
            Field[] fields = taskClass.getFields();

            //get all private fields
            Field[] declaredFields = taskClass.getDeclaredFields();

            //set new value for public field
            titleField.set(task, "new title");

            //set new value for private field
            Type type = idField.getAnnotatedType().getType();
            if (Long.TYPE.equals(type)) {
                idField.setAccessible(true);
                idField.setLong(task, 99);
            }

            //get annotations
            boolean isPresentAnnotation = Arrays.stream(idField.getDeclaredAnnotations())
                    .anyMatch(annotation -> annotation.annotationType().equals(MyAnnotation.class));

            //get annotation by type
            MyAnnotation[] declaredAnnotationsByType = idField.getDeclaredAnnotationsByType(MyAnnotation.class);

            //get modifiers
            int modifiers = idField.getModifiers();
            boolean aPublic = Modifier.isPublic(modifiers);
            boolean aStatic = Modifier.isStatic(modifiers);
            boolean aPrivate = Modifier.isPrivate(modifiers);
            boolean aFinal = Modifier.isFinal(modifiers);

            //get generic info
            Field tField = taskClass.getDeclaredField("strings");
            Type genericType = tField.getGenericType();
            if (genericType instanceof ParameterizedType parameterizedType) {
                String typeName = parameterizedType.getTypeName();
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                System.out.println();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
