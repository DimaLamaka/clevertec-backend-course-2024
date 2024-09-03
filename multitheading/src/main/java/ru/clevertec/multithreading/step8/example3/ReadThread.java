package ru.clevertec.multithreading.step8.example3;

import java.util.concurrent.locks.ReadWriteLock;

public class ReadThread extends Thread {
    private final ReadWriteLockResource readWriteLockResource;
    private final ReadWriteLock readWriteLock;

    public ReadThread(String name, ReadWriteLockResource readWriteLockResource, ReadWriteLock readWriteLock) {
        super(name);
        this.readWriteLockResource = readWriteLockResource;
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void run() {
        try {
            readWriteLock.readLock().lock();
            System.out.println(Thread.currentThread() + ": Read lock is lock");
            System.out.println(Thread.currentThread() + ": <<READ>> State is: \n" + readWriteLockResource.read());
        } finally {
            System.out.println(Thread.currentThread() + ": Read lock is unlock");
            readWriteLock.readLock().unlock();
        }
    }
}
