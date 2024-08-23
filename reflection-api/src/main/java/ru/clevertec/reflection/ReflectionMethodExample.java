package ru.clevertec.reflection;

import java.lang.annotation.*;
import java.lang.reflect.AccessFlag;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Set;

public class ReflectionMethodExample {
    public static void main(String[] args) {
        Class<Task> taskClass = Task.class;
        Task task = new Task();

        try {
            //get and invoke public method
            Method updateTask = taskClass.getMethod("updateTask", String.class, int.class);
            Object description = updateTask.invoke(task, "description", 1);

            //get and invoke private method
            Method secretTask = taskClass.getDeclaredMethod("secretTask");
            secretTask.setAccessible(true);
            Object invoke = secretTask.invoke(task);

            //get and invoke private static method
            Method staticMethod = taskClass.getDeclaredMethod("staticMethod");
            secretTask.setAccessible(true);
            Object staticMethodInvoke = staticMethod.invoke(null);

            //get annotations
            Annotation[] declaredAnnotations = secretTask.getDeclaredAnnotations();
            TaskInfo annotation = secretTask.getAnnotation(TaskInfo.class);

            //get return type info
            Method getInfo = taskClass.getDeclaredMethod("getInfo");
            Type type = getInfo.getAnnotatedReturnType().getType();
            Annotation[] declaredAnnotationsReturnType = getInfo.getAnnotatedReturnType().getDeclaredAnnotations();
            Class<?> returnType = getInfo.getReturnType();

            //get parameters info
            Class<?>[] parameterTypes = updateTask.getParameterTypes();
            Annotation[][] parameterAnnotations = updateTask.getParameterAnnotations();

            //get exceptions
            Class<?>[] exceptionTypes = updateTask.getExceptionTypes();

            //get default value for annotation methods
            Object defaultValue = TaskInfo.class.getMethod("description");

            //modifiers
            int modifiers = updateTask.getModifiers();
            boolean isPrivate = Modifier.isPrivate(modifiers);
            boolean isPublic = Modifier.isPublic(modifiers);
            Set<AccessFlag> accessFlags = updateTask.accessFlags();
            System.out.println();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    static class Task {
        public void updateTask(@TaskInfo String description, int priority) throws OutOfMemoryError {
            System.out.println("Updating task: " + description + ", Priority: " + priority);
        }

        @TaskInfo(description = "secret task method")
        private void secretTask() {
            System.out.println("Secret task executed.");
        }

        private @TaskInfo String getInfo() {
            return "info";
        }

        private static void staticMethod() {
            System.out.println("Static method executed.");
        }
    }

    @Target({ElementType.METHOD, ElementType.TYPE_USE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface TaskInfo {
        String description() default "default description";
    }
}
