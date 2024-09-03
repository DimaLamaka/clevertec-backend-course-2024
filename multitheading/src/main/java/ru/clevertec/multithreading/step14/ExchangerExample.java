package ru.clevertec.multithreading.step14;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class ExchangerExample {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        Thread thread1 = new Thread(() -> {
            try {
                String message = "Hello from Thread 1";
                System.out.println("Thread 1 sends: " + message);
                String receivedMessage = exchanger.exchange(message);
                System.out.println("Thread 1 received: " + receivedMessage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                String message = "Hello from Thread 2";
                System.out.println("Thread 2 sends: " + message);
                String receivedMessage = exchanger.exchange(message);
                System.out.println("Thread 2 received: " + receivedMessage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                String message = "Hello from Thread 3";
                System.out.println("Thread 3 sends: " + message);
                String receivedMessage = exchanger.exchange(message);
                System.out.println("Thread 3 received: " + receivedMessage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread4 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                String message = "Hello from Thread 4";
                System.out.println("Thread 4 sends: " + message);
                String receivedMessage = exchanger.exchange(message);
                System.out.println("Thread 4 received: " + receivedMessage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
