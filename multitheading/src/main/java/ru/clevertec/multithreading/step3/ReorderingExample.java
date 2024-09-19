package ru.clevertec.multithreading.step3;


/**
 * Check tests
 */
public class ReorderingExample {
    private static int x = 0;
    private static int y = 0;
    private static int a = 0;
    private static int b = 0;

    public static void main(String[] args) throws InterruptedException {
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
    }
}
