package ru.clevertec.multithreading.step2;

import java.util.concurrent.TimeUnit;

public class InterruptExample {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread() + ": Working...");
            }
            System.out.println("Thread1 is interrupted: " + Thread.currentThread().isInterrupted() + "; state: " + Thread.currentThread().getState());
        }, "FIRST_THREAD");

        //sleep, wait, join
        Thread thread2 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println(Thread.currentThread() + ": Working...");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread() + " Interrupted with exception!");
                    Thread.currentThread().interrupt();
                }
            }
        }, "SECOND_THREAD");

        thread.start();
        thread2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
            thread.interrupt();
            thread.join();
            System.out.println("Thread1 is interrupted: " + thread.isInterrupted() + "; state: " + thread.getState());

            thread2.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
