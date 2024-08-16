package ru.clevertec.parallelstream;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class ParallelStreamExample {

    public static void main(String[] args) {
//        showParallelStreamExample();
        forkJoinPoolExample();
    }

    public static void showParallelStreamExample() {
        List<Integer> numbers = IntStream.rangeClosed(1, 10_000_000).boxed().toList();

        long startTime = System.currentTimeMillis();

        int sum = numbers.parallelStream()
                .filter(num -> num % 2 == 0)
                .mapToInt(Integer::intValue)
                .sum();

        long endTime = System.currentTimeMillis();
        System.out.println("Sum of even numbers: " + sum);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");


        List<String> words = Arrays.asList("parallel", "stream", "optimization", "order", "example");

        words.parallelStream()
                .map(String::toUpperCase)
                .peek(name-> System.out.println("Thread: " + Thread.currentThread().getName()))
                .forEach(System.out::println);
    }

    public static void forkJoinPoolExample() {
        List<Integer> numbers = IntStream.rangeClosed(1, 10_000_000).boxed().toList();

        try (ForkJoinPool customThreadPool = new ForkJoinPool(2)) {
            long startTime = System.currentTimeMillis();

            customThreadPool.submit(() -> {
                int sum = numbers.parallelStream()
                        .filter(num -> num % 2 == 0)
                        .peek(name-> System.out.println("Thread: " + Thread.currentThread().getName()))
                        .mapToInt(Integer::intValue)
                        .sum();

                System.out.println("Sum of even numbers: " + sum);
            }).join(); // Ensure the computation completes

            long endTime = System.currentTimeMillis();

            System.out.println("Time taken: " + (endTime - startTime) + " ms");

            customThreadPool.shutdown();
        }
    }
}
