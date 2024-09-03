package ru.clevertec.multithreading.step10;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SemaphoreExample {
    public static void main(String[] args) throws InterruptedException {
        Semaphore cashboxes = new Semaphore(2);
        List<Thread> buyerThreads = Stream.of(
                        new BuyerThread(cashboxes),
                        new BuyerThread(cashboxes),
                        new BuyerThread(cashboxes),
                        new BuyerThread(cashboxes),
                        new BuyerThread(cashboxes),
                        new BuyerThread(cashboxes),
                        new BuyerThread(cashboxes),
                        new BuyerThread(cashboxes)
                )
                .map(Thread::new)
                .peek(Thread::start)
                .toList();

        for (Thread thread : buyerThreads) {
            thread.join();
        }
    }
}
