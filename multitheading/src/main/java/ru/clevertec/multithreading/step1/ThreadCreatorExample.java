package ru.clevertec.multithreading.step1;

import java.util.concurrent.*;

public class ThreadCreatorExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Start...");

        WriterThread writerThread = new WriterThread("writerThread");

        Thread runnableThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i + ": " + Thread.currentThread());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "runnableThread");

        runnableThread.start();
        writerThread.start();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> callResult = executorService.submit(new CallThread());
        String result = callResult.get();
        System.out.println("call result is: " + result);
        executorService.shutdown();

        runnableThread.join();
        writerThread.join();

        System.out.println("End...");
    }
}
