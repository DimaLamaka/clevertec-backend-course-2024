package ru.clevertec.multithreading.step9;


import lombok.SneakyThrows;

public class RaceConditionExample {
    private static int counter = 0;

    @SneakyThrows
    public static void main(String[] args) {

        Thread writerThread = new Thread(() -> addedToCounter(10));

        Thread writerThread2 = new Thread(() -> addedToCounter(10));

        writerThread.start();
        writerThread2.start();

        writerThread.join();
        writerThread2.join();

        System.out.println("Result counter is = " + counter);
    }

    private static void addedToCounter(int value){
        for (int i = 0; i < 1_000_000; i++) {
            counter += value;
        }
    }
}
