package ru.clevertec.multithreading.step6;

import lombok.SneakyThrows;

public class SingletonHolder {
    static SingletonHolder getInstance() {
        System.out.println("Trying to get instance");
        return Holder.INSTANCE;
    }

    private SingletonHolder() {
        System.out.println("Current thread is " + Thread.currentThread());
    }

    private static class Holder {
        static final SingletonHolder INSTANCE = new SingletonHolder();
    }

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("Start handle");
        Thread thread = new Thread(()-> System.out.println(SingletonHolder.getInstance()));
        Thread thread1 = new Thread(()-> System.out.println(SingletonHolder.getInstance()));
        Thread thread2 = new Thread(()-> System.out.println(SingletonHolder.getInstance()));

        thread.start();
        thread1.start();
        thread2.start();

        thread.join();
        thread1.join();
        thread2.join();
    }
}

