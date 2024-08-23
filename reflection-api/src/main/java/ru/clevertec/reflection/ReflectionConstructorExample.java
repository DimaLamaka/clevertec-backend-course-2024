package ru.clevertec.reflection;

import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

public class ReflectionConstructorExample {
    public static void main(String[] args) {
        Class<Task> taskClass = Task.class;

        try {
            //get default constructor
            Constructor<Task> defaultConstructor = taskClass.getConstructor();

            //get constructor by parameters
            Constructor<Task> constructor = taskClass.getConstructor(String.class, int.class);

            //get all constructors
            Constructor<?>[] constructors = taskClass.getConstructors();

            String name = constructor.getName();
            int parameterCount = constructor.getParameterCount();
            Parameter[] parameters = constructor.getParameters();
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            int modifiers = constructor.getModifiers();
            Annotation[] declaredAnnotations = constructor.getDeclaredAnnotations();
            Class<?>[] exceptionTypes = constructor.getExceptionTypes();
            Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();

            //create new instance
            Task task = constructor.newInstance("Hello World", 1);
            System.out.println();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    static class Task {
        private String description;
        private int priority;
        private boolean isCompleted;

        public Task() {
            this.description = "No Description";
            this.priority = 0;
            this.isCompleted = false;
        }

        public Task(String description) {
            this.description = description;
            this.priority = 0;
            this.isCompleted = false;
        }

        @ConstructorAnnotation
        public Task(@ConstructorAnnotation String description, int priority) throws ArrayIndexOutOfBoundsException {
            this.description = description;
            this.priority = priority;
            this.isCompleted = false;
        }

        public Task(String description, int priority, boolean isCompleted) {
            this.description = description;
            this.priority = priority;
            this.isCompleted = isCompleted;
        }

        @Override
        public String toString() {
            return "Task{" +
                    "description='" + description + '\'' +
                    ", priority=" + priority +
                    ", isCompleted=" + isCompleted +
                    '}';
        }
    }

    @Target({ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface ConstructorAnnotation {
    }
}
