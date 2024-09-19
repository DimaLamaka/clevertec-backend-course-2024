package ru.clevertec.multithreading.step4;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceExample {

    public static void main(String[] args) {
        AtomicReference<String> stringRef = new AtomicReference<>("Init value ");

        Thread thread1 = new Thread(() -> {
            boolean success = false;
            while (!success){
                String prevValue = stringRef.get();
                String nextValue = stringRef.get() + "thread1 value ";
                success = stringRef.compareAndSet(prevValue,nextValue);
            }
        });

        Thread thread2 = new Thread(() -> {
            boolean success = false;
            while (!success){
                String prevValue = stringRef.get();
                String nextValue = stringRef.get() + "thread1 value ";
                success = stringRef.compareAndSet(prevValue,nextValue);
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("New value = " + stringRef.get());
    }
}
