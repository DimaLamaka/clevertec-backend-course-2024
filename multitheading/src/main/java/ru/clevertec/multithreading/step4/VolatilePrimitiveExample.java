package ru.clevertec.multithreading.step4;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class VolatilePrimitiveExample {
    private volatile static int intHolder;

    @SneakyThrows
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1_000_000; j++) {
                    synchronized (VolatilePrimitiveExample.class) {
                        intHolder++;
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("intHolder = " + intHolder);
    }
}
