package ru.clevertec.multithreading.step15;

import lombok.SneakyThrows;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueExample {

    @SneakyThrows
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(5);
        InThread inThread = new InThread("inThread", blockingQueue);
        OutThread outThread = new OutThread("outThread", blockingQueue);

        inThread.start();
        outThread.start();

        TimeUnit.SECONDS.sleep(10);
        System.out.println(blockingQueue);
    }
}
