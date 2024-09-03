package ru.clevertec.multithreading.step5;

public class ThreadLocalExample {
    private static final ThreadLocal<Integer> COUNTER = ThreadLocal.withInitial(() -> -1);

    public static void setCounter(int value) {
        COUNTER.set(value);
    }

    public static int getCounter() {
        return COUNTER.get();
    }

    public static void main(String[] args) {
        setCounter(42);
        System.out.println(Thread.currentThread() + " " + getCounter());

        Thread firstThread = new Thread(() -> {
            System.out.println(Thread.currentThread() + " " + getCounter());
            setCounter(33);
            System.out.println(Thread.currentThread() + " " + getCounter());
        }, "FIRST_WORKER");

        Thread secondThread = new Thread(() -> System.out.println(Thread.currentThread() + " " + getCounter()), "SECOND_WORKER");
        WorkerThread workerThread = new WorkerThread("THIRD_WORKER");

        firstThread.start();
        secondThread.start();
        workerThread.start();

        try {
            firstThread.join();
            System.out.println(Thread.currentThread() + " " + getCounter());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static class WorkerThread extends Thread {
        public WorkerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread() + " " + ThreadLocalExample.getCounter());
            ThreadLocalExample.setCounter(666);
            System.out.println(Thread.currentThread() + " " + ThreadLocalExample.getCounter());
        }
    }
}
