package ru.clevertec.multithreading.step3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;


class ReorderingExampleTest {

    private static int x = 0;
    private static int y = 0;
    private static int a = 0;
    private static int b = 0;


    @RepeatedTest(10_000)
    void test() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            a = 1;
            x = b;
        });
        Thread thread2 = new Thread(() -> {
            b = 1;
            y = a;
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("x = " + x + ", y = " + y);
        Assertions.assertFalse(x == 0 && y == 0);
    }

    @AfterEach
    void tearDown() {
        x = 0;
        y = 0;
        a = 0;
        b = 0;
    }
}