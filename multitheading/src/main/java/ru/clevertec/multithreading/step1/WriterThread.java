package ru.clevertec.multithreading.step1;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class WriterThread extends Thread {
    public WriterThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        IntStream.range(0, 10)
                .peek(i -> sleep(1))
                .forEach(i -> System.out.println(i + ": " + Thread.currentThread()));
    }

    @SneakyThrows
    private void sleep(int seconds) {
        TimeUnit.SECONDS.sleep(seconds);
    }
}
