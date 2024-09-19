package ru.clevertec.multithreading.step15;

import java.util.concurrent.BlockingQueue;

public class InThread extends Thread {
    private final BlockingQueue<String> blockingQueue;

    public InThread(String name, BlockingQueue<String> blockingQueue) {
        super(name);
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                blockingQueue.put("Number " + i);
                System.out.println("Put number " + i);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
