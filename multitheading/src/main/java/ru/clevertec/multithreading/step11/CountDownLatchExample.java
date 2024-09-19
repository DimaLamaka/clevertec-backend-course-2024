package ru.clevertec.multithreading.step11;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CountDownLatchExample {
    @SneakyThrows
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        IntStream.range(0, 25)
                .mapToObj(i -> new PassengerThread(i + " passenger", countDownLatch))
                .forEach(PassengerThread::start);

        countDownLatch.await();
        System.out.println("All passengers have arrived. Transport leaves");
    }


    static class PassengerThread extends Thread {
        private final Random random = new Random();
        private final CountDownLatch countDownLatch;

        public PassengerThread(String name, CountDownLatch countDownLatch) {
            super(name);
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(5) + (long) 2);
                System.out.println(getName() + ": Passenger reached the transport");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}
