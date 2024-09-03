package ru.clevertec.multithreading.step1;

import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public class CallThread implements Callable<String> {
    @Override
    public String call() {
        StringBuilder stringBuilder = new StringBuilder();
        IntStream.range(0, 10)
                .mapToObj(i -> "call" + i + " ")
                .forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
