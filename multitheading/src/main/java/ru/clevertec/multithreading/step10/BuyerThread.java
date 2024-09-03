package ru.clevertec.multithreading.step10;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class BuyerThread implements Runnable {
    private final Semaphore cashboxes;

    public BuyerThread(Semaphore cashboxes) {
        this.cashboxes = cashboxes;
    }

    @Override
    public void run() {
        try {
            cashboxes.acquire();
            System.out.println(Thread.currentThread().getName() + " served at the cashbox");

            TimeUnit.SECONDS.sleep(5);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println(Thread.currentThread().getName() + " release cashbox");
            cashboxes.release();
        }

    }
}
