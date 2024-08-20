package ru.clevertec.unit.testing.step2.annotations.parallel;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

//ResourceLock
@Execution(ExecutionMode.CONCURRENT)
class ParallelStreamTest {
    private static final List<String> STRINGS = new ArrayList<>();

    @Test
    @ResourceLock(value = "1", mode = ResourceAccessMode.READ_WRITE)
    void firstTest() {
        System.out.println(Thread.currentThread().getName() + " thread start");
        IntStream.range(0, 1_000_000).forEach(i -> STRINGS.add("string" + i));
        Assertions.assertTrue(true);
        System.out.println(Thread.currentThread().getName() + " thread end");
    }

    @Test
    @ResourceLock(value = "1",mode = ResourceAccessMode.READ_WRITE)
    void secondTest() {
        System.out.println(Thread.currentThread().getName() + " thread start");
        IntStream.range(0, 1_000_000).forEach(i -> STRINGS.add("string" + i));
        Assertions.assertTrue(true);
        System.out.println(Thread.currentThread().getName() + " thread end");
    }

    @SneakyThrows
    @Test
    @ResourceLock(value = "1",mode = ResourceAccessMode.READ)
    void thirdTest() {
        TimeUnit.MICROSECONDS.sleep(20);
        System.out.println(Thread.currentThread().getName() + " thread start");
        System.out.println("STRINGS COUNT FROM THREAD IS -> " + STRINGS.size());
        Assertions.assertTrue(true);
        System.out.println(Thread.currentThread().getName() + " thread end");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("STRINGS COUNT IS -> " + STRINGS.size());
    }
}
