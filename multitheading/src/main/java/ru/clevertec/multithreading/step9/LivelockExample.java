package ru.clevertec.multithreading.step9;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LivelockExample {
    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);


    public static void main(String[] args) {
        LivelockExample livelock = new LivelockExample();
        new Thread(livelock::operationA, "thread1").start();
        new Thread(livelock::operationB, "thread2").start();
    }

    @SneakyThrows
    public void operationA() {
        while (true) {
            lock1.lock();
            System.out.println(Thread.currentThread() + ": lock1 acquired, trying to acquire lock2.");
            TimeUnit.MILLISECONDS.sleep(50);

            if (lock2.tryLock(1, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread() + ": lock2 acquired.");
            } else {
                System.out.println(Thread.currentThread() + ": cannot acquire lock2, releasing lock1.");
                lock1.unlock();
                continue;
            }

            System.out.println(Thread.currentThread() + ": executing first operation.");
            break;
        }
        lock2.unlock();
        lock1.unlock();
    }

    @SneakyThrows
    public void operationB() {
        while (true) {
            lock2.lock();
            System.out.println(Thread.currentThread() + ": lock2 acquired, trying to acquire lock1.");
            TimeUnit.MILLISECONDS.sleep(50);

            if (lock1.tryLock()) {
                System.out.println(Thread.currentThread() + ": lock1 acquired.");
            } else {
                System.out.println(Thread.currentThread() + ": cannot acquire lock1, releasing lock2.");
                lock2.unlock();
                continue;
            }

            System.out.println(Thread.currentThread() + ": executing second operation.");
            break;
        }
        lock1.unlock();
        lock2.unlock();
    }
}
