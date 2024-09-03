package ru.clevertec.multithreading.step8.example3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;

public class WriterThread extends Thread {
    private final ReadWriteLockResource readWriteLockResource;
    private final ReadWriteLock readWriteLock;

    public WriterThread(String name, ReadWriteLockResource readWriteLockResource, ReadWriteLock readWriteLock) {
        super(name);
        this.readWriteLockResource = readWriteLockResource;
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void run() {
        try {
            readWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread() + ": Write lock is lock");
            for (int i = 0; i < 5; i++) {
                readWriteLockResource.write(getName() + " " + i);
                if (i == 2) {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println(Thread.currentThread() + ": Writer is sleeping");
                }
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread() + ": Write lock is unlock");
            readWriteLock.writeLock().unlock();
        }
    }
}
