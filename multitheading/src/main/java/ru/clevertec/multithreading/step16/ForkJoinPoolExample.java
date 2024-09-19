package ru.clevertec.multithreading.step16;

import lombok.AllArgsConstructor;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolExample {
    private static final int THRESHOLD = 1_000;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        int[] array = new int[1_000_000_000];
        long max = Long.MIN_VALUE;

        for (int i = 0; i < array.length; i++) {
            int nextInt = RANDOM.nextInt();
            array[i] = nextInt;
            max = Math.max(max, nextInt);
        }

        System.out.println("Array size : " + array.length + " , max value = " + max);
        System.out.println("--------------------------------------------------------");

        System.out.println("Start default recursive find max ");
        long start = System.nanoTime();
        long maxWithSingleThreadRecursive = findMaxWithSingleThreadRecursive(array, 0, array.length);
        long time = System.nanoTime() - start;
        System.out.println("End default recursive find max for: " + TimeUnit.NANOSECONDS.toMillis(time) + ", and max value = " + maxWithSingleThreadRecursive);

        System.out.println("Start multithreading recursive task find max ");
        start = System.nanoTime();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Long maxFromMultithreadingTask = forkJoinPool.invoke(new RecursiveFindMaxTask(array, 0, array.length));
        time = System.nanoTime() - start;
        System.out.println("End multithreading recursive task find max for: " + TimeUnit.NANOSECONDS.toMillis(time) + ", and max value = " + maxFromMultithreadingTask);
        forkJoinPool.shutdown();
    }

    private static long findMaxWithSingleThreadRecursive(int[] array, int start, int end) {
        if (end - start <= THRESHOLD) {
            long max = Long.MIN_VALUE;
            for (int i = start; i < end; i++) {
                max = Math.max(max, array[i]);
            }
            return max;
        }
        int middle = (start + end) / 2;
        long left = findMaxWithSingleThreadRecursive(array, start, middle);
        long right = findMaxWithSingleThreadRecursive(array, middle, end);

        return Math.max(left, right);
    }

    @AllArgsConstructor
    static class RecursiveFindMaxTask extends RecursiveTask<Long> {
        private final int[] array;
        private final int start;
        private final int end;

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                long max = Long.MIN_VALUE;
                for (int i = start; i < end; i++) {
                    max = Math.max(max, array[i]);
                }
                return max;
            }
            int middle = (start + end) / 2;
            RecursiveFindMaxTask leftTask = new RecursiveFindMaxTask(array, start, middle);
            RecursiveFindMaxTask rightTask = new RecursiveFindMaxTask(array, middle, end);

            leftTask.fork();
            rightTask.fork();

            return Math.max(leftTask.join(), rightTask.join());
        }
    }
}