package ru.clevertec.multithreading.step4;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicPrimitiveExample {
    private static final AtomicInteger INTEGER_HOLDER = new AtomicInteger();

    @SneakyThrows
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1_000_000; j++) {
                    INTEGER_HOLDER.getAndIncrement();
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("counter = " + INTEGER_HOLDER.get());
    }
}
