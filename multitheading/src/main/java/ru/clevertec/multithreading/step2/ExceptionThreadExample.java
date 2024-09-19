package ru.clevertec.multithreading.step2;

import lombok.SneakyThrows;

public class ExceptionThreadExample {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(Thread.currentThread() + " started");

        ExceptionThread exceptionThread = new ExceptionThread();
        exceptionThread.setName("EXCEPTION_THREAD");
        Thread workThread = new Thread(() -> System.out.println(Thread.currentThread() + " - working..."));
        workThread.setName("WORK_THREAD");

        exceptionThread.start();
        workThread.start();

        exceptionThread.join();
        workThread.join();

        System.out.println(Thread.currentThread() + " ended");
    }

    static class ExceptionThread extends Thread {
        @Override
        public void run() {
            throw new RuntimeException();
        }
    }
}
