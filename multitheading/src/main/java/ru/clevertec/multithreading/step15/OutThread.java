package ru.clevertec.multithreading.step15;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class OutThread extends Thread {
    private BlockingQueue<String> blockingQueue;

    public OutThread(String name, BlockingQueue<String> blockingQueue) {
        super(name);
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                String take = blockingQueue.poll(2, TimeUnit.HOURS); //remove, poll, take
                System.out.println("take number " + take);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
