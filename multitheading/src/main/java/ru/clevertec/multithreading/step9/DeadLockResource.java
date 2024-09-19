package ru.clevertec.multithreading.step9;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeadLockResource {

    @Data
    @AllArgsConstructor
    static class Resource {
        private int value;
    }

    public static void main(String[] args) {
        Resource firstResource = new Resource(1);
        Resource secondResource = new Resource(2);
        Comparator<Resource> resourceComparator = Comparator.comparingInt(Resource::getValue);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> handle(firstResource, secondResource, resourceComparator));
        executorService.submit(() -> handle(secondResource, firstResource, resourceComparator));

        executorService.shutdown();
    }

    public static void handle(Resource resource1, Resource resource2, Comparator<Resource> comparator) {
        Object lock1 = resource1;
        Object lock2 = resource2;
        if (comparator.compare(resource1, resource2) > 0) {
            lock1 = resource2;
            lock2 = resource1;
        }
        System.out.println(Thread.currentThread().getName() + " start handle");
        synchronized (lock1) {
            try {
                System.out.println(Thread.currentThread().getName() + " get lock1");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + " get lock2");
                resource1.setValue(resource2.getValue());
            }
        }
        System.out.println(Thread.currentThread().getName() + " end handle");
    }
}
