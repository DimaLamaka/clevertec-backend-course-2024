package ru.clevertec.multithreading.step12;

import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CyclicBarrierExample {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("START");

        int numberOfThreads = 8;
        CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("All threads have reached the barrier"));

        List<Thread> threads = IntStream.range(0, numberOfThreads)
                .mapToObj(i -> new Thread(new Worker(i, barrier)))
                .peek(Thread::start)
                .peek(thread -> {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("END");
    }

    static class Worker implements Runnable {
        private final int id;
        private final CyclicBarrier barrier;

        public Worker(int id, CyclicBarrier barrier) {
            this.id = id;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("Worker " + id + " is performing some task");
                TimeUnit.SECONDS.sleep(2);
                barrier.await(10, TimeUnit.SECONDS);
                System.out.println("Worker " + id + " continues execution after barrier");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}