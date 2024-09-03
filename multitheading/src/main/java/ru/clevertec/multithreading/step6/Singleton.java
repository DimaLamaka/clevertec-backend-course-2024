package ru.clevertec.multithreading.step6;

import lombok.SneakyThrows;

public class Singleton {
    private static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    @SneakyThrows
    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println(Singleton.getInstance()));
        Thread thread1 = new Thread(() -> System.out.println(Singleton.getInstance()));
        Thread thread2 = new Thread(() -> System.out.println(Singleton.getInstance()));
        Thread thread3 = new Thread(() -> System.out.println(Singleton.getInstance()));
        Thread thread4 = new Thread(() -> System.out.println(Singleton.getInstance()));
        Thread thread5 = new Thread(() -> System.out.println(Singleton.getInstance()));
        Thread thread6 = new Thread(() -> System.out.println(Singleton.getInstance()));
        Thread thread7 = new Thread(() -> System.out.println(Singleton.getInstance()));

        thread.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();

        thread.join();
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        thread6.join();
        thread7.join();
    }
}
