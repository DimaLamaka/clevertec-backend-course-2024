package ru.clevertec.multithreading.step6;

import lombok.SneakyThrows;


public class ResourceExample {

    @SneakyThrows
    public static void main(String[] args) {

        StringBuilder firstResource = new StringBuilder();
        StringBuilder secondResource = new StringBuilder();
        Resource resource = new Resource(firstResource, secondResource);

        Thread firstThread = new Thread(() -> resource.appendToFirstResource(Thread.currentThread().getName()));
        Thread secondThread = new Thread(() -> resource.appendToFirstResource(Thread.currentThread().getName()));
        Thread thirdThread = new Thread(() -> resource.appendToSecondResource(Thread.currentThread().getName()));
        Thread fourthThread = new Thread(() -> resource.appendToSecondResource(Thread.currentThread().getName()));

        firstThread.start();
        secondThread.start();
        thirdThread.start();
        fourthThread.start();

        firstThread.join();
        secondThread.join();
        thirdThread.join();
        fourthThread.join();

        System.out.println("FIRST RESOURCE");
        System.out.println(firstResource);
        System.out.println("SECOND RESOURCE");
        System.out.println(secondResource);
    }
}
