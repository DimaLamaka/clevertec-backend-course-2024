package ru.clevertec.multithreading.step2;

import lombok.SneakyThrows;

import java.util.Arrays;

public class ThreadPriorityExample {

    @SneakyThrows
    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("PRIORITY_GROUP");
//        group.setMaxPriority(Thread.MAX_PRIORITY);
        PriorityThread firstPriorityThread = new PriorityThread("FIRST_PRIORITY_THREAD", group);
        PriorityThread secondPriorityThread = new PriorityThread("SECOND_PRIORITY_THREAD", group);

        firstPriorityThread.setPriority(Thread.MIN_PRIORITY);
        secondPriorityThread.setPriority(Thread.MAX_PRIORITY);

        firstPriorityThread.start();
        secondPriorityThread.start();

        Thread[] threads = new Thread[2];
        group.enumerate(threads);

        firstPriorityThread.join();
        secondPriorityThread.join();

        System.out.println("Threads in group: " + Arrays.toString(threads));
    }

    static class PriorityThread extends Thread {
        public PriorityThread(String name, ThreadGroup threadGroup) {
            super(threadGroup, name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 1_000; i++) {
                System.out.println(i + " task, " + Thread.currentThread());
            }
        }
    }
}
