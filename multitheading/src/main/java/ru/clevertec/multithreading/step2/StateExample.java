package ru.clevertec.multithreading.step2;

import java.util.concurrent.TimeUnit;

public class StateExample {
    public static void main(String[] args) throws InterruptedException {
        Thread runnableThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i + ": " + Thread.currentThread());
                try {
                    TimeUnit.SECONDS.sleep(1);
                    if (i == 5) {
                        TimeUnit.SECONDS.sleep(5);
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "RUNNABLE_THREAD");
        System.out.println(runnableThread.getName() + " state is: " + runnableThread.getState());

        runnableThread.start();
        System.out.println(runnableThread.getName() + " state is: " + runnableThread.getState());

        TimeUnit.SECONDS.sleep(8);
        System.out.println(runnableThread.getName() + " state is: " + runnableThread.getState());

        runnableThread.join();
        System.out.println(runnableThread.getName() + " state is: " + runnableThread.getState());

        System.out.println(runnableThread.getName() + " is alive: " + runnableThread.isAlive());
    }
}
