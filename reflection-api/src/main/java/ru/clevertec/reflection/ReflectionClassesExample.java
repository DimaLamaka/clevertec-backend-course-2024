package ru.clevertec.reflection;

import java.lang.annotation.*;
import java.lang.reflect.AccessFlag;
import java.lang.reflect.Modifier;
import java.util.Set;

public class ReflectionClassesExample {
    public static void main(String[] args) {
        try {
            //init class
            Class<String> stringClass = String.class;
            Class<? extends String> secondStringClass = "".getClass();
            Class<?> aClass = Class.forName("java.lang.String");
            Class<Integer> integerClass = int.class;
            boolean isInteger = Integer.TYPE.equals(integerClass);

            //names
            Class<Task> taskClass = Task.class;
            String name = taskClass.getName();
            String canonicalName = taskClass.getCanonicalName();
            String simpleName = taskClass.getSimpleName();
            Package aPackage = taskClass.getPackage();
            String packageName = taskClass.getPackageName();

            //super class and interfaces
            Class<? super Task> superclass = taskClass.getSuperclass();
            Class<?>[] interfaces = taskClass.getInterfaces();

            //modifiers
            Set<AccessFlag> accessFlags = taskClass.accessFlags();
            int modifiers = taskClass.getModifiers();
            boolean isStatic = Modifier.isStatic(modifiers);


            //cast
            Task task = new Task();
            BaseTask castTask = BaseTask.class.cast(task);
            Class<? extends BaseTask> subclass = taskClass.asSubclass(BaseTask.class);

            //base task is super class for task
            boolean assignableFrom = BaseTask.class.isAssignableFrom(Task.class);

            //annotations
            Annotation[] declaredAnnotations = taskClass.getDeclaredAnnotations();
            Annotation[] annotations = taskClass.getAnnotations();
            ClassAnnotation annotation = taskClass.getAnnotation(ClassAnnotation.class);

            //get public inner classes
            Class<?>[] classes = taskClass.getClasses();
            //get all inner classes
            Class<?>[] declaredClasses = taskClass.getDeclaredClasses();

            //class loader
            ClassLoader classLoader = taskClass.getClassLoader();
            String classLoaderName = classLoader.getName();
            ClassLoader parent = classLoader.getParent();
            String classLoaderParentName = parent.getName();

            Class<?> stringClassFromCL = classLoader.loadClass("java.lang.String");

            //checks
            boolean anEnum = taskClass.isEnum();
            boolean array = taskClass.isArray();
            boolean primitive = taskClass.isPrimitive();
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @ClassAnnotation
    static class Task extends BaseTask implements Runnable, Cloneable2 {
        @Override
        public void run() {
            System.out.println("Task is running.");
        }

        public static class PublicInnerClass {}
        private static class PrivateInnerClass {}
    }

    @ClassAnnotation
    @InterfaceAnnotation
    static class BaseTask {
    }

    @InterfaceAnnotation
    interface Cloneable2 {

    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ClassAnnotation {

    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @interface InterfaceAnnotation {

    }
}
