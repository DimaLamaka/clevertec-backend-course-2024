package ru.clevertec.multithreading.step9;

import java.util.concurrent.TimeUnit;

public class DeadLock {
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread thread1 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread() + ": Start handle");
                    synchronized (lock1) {
                        System.out.println(Thread.currentThread() + ": lock first lock");
                        try {
                            TimeUnit.MILLISECONDS.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (lock2) {
                            System.out.println(Thread.currentThread() + ": lock second lock");
                        }
                    }
                    System.out.println(Thread.currentThread() + ": End handle");
                }
                , "FIRST_THREAD");

        Thread thread2 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread() + ": Start handle");
                    synchronized (lock2) {
                        System.out.println(Thread.currentThread() + ": lock second lock");
                        synchronized (lock1) {
                            System.out.println(Thread.currentThread() + ": lock first lock");
                        }
                    }
                    System.out.println(Thread.currentThread() + ": End handle");
                }
                , "SECOND_THREAD");

        thread1.start();
        thread2.start();
    }
}
