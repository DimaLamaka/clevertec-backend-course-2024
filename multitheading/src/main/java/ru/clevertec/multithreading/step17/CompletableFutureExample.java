package ru.clevertec.multithreading.step17;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompletableFutureExample {
    interface Worker {
        String doWork();
    }

    private static final List<Worker> WORKERS = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++) {
            int copy = i;
            WORKERS.add(() -> {
                System.out.println(Thread.currentThread().getName() + " <<<<< STARTED WORK - " + copy);
                String value = Thread.currentThread().getName() + " <<<<< DO WORK - " + copy;
                System.out.println(value);
                sleep(1);
                System.out.println(Thread.currentThread().getName() + " <<<<< END WORK - " + copy);
                return value;
            });
        }
    }

    public static void main(String[] args) {
        List<String> results = WORKERS.stream()
                .map(worker -> CompletableFuture.supplyAsync(worker::doWork))
                .map(CompletableFuture::join)
                .toList();
        System.out.println("RESULT 1 IS: " + results);
        System.out.println("------------------------------------");

        List<CompletableFuture<String>> tasks = WORKERS.stream()
                .map(worker -> CompletableFuture.supplyAsync(worker::doWork))
                .toList();

        String result = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]))
                .thenApply(all -> tasks.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .thenApply(List::toString)
                .completeOnTimeout("default value", 5, TimeUnit.SECONDS)
                .thenApplyAsync(str -> {
                    System.out.println(Thread.currentThread().getName() + " ------ UPPER CASE");
                    return str.toUpperCase();
                })
                .exceptionally(thr -> {
                    throw new RuntimeException();
                })
                .handle((res, th) -> th == null ? res : "exc")
//                .orTimeout(5, TimeUnit.SECONDS)
                .join();

        System.out.println("RESULT 2 IS: " + result);
    }

    @SneakyThrows
    private static void sleep(long seconds) {
        TimeUnit.SECONDS.sleep(seconds);
    }
}
