package ru.clevertec.multithreading.step8.example1;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(100);

        Thread programmer = new Thread(account::goDrink, "programmer0");
        Thread programmer1 = new Thread(account::goDrink,"programmer1");
        Thread programmer2 = new Thread(account::goDrink,"programmer2");
        Thread programmer3 = new Thread(account::goDrink,"programmer3");
        Thread programmer4 = new Thread(account::goDrink,"programmer4");

        programmer.start();
        programmer1.start();
        programmer2.start();
        programmer3.start();
        programmer4.start();

        for (int i = 0; i < 5; i++) {
            TimeUnit.SECONDS.sleep(5);
            new Thread(() -> account.addBalance(1000),"bank #" + i).start();
        }
    }

    static class Account {
        private int balance;
        private Lock lock = new ReentrantLock(true);
        private Condition condition = lock.newCondition();

        public Account(int balance) {
            this.balance = balance;
        }

        @SneakyThrows
        public void goDrink() {
            try {
                lock.lock();
                while (balance < 1000) {
                    System.out.println(Thread.currentThread().getName() + ". Balance is " + balance);
                    condition.await();
                }
                System.out.println(Thread.currentThread().getName() + ". I'm going to drink");
                balance = 0;
                System.out.println(Thread.currentThread().getName() + ". Balance is " + balance);
            } finally {
                lock.unlock();
            }
        }

        @SneakyThrows
        public void addBalance(int sum) {
            try {
                lock.lock();
                balance += sum;
                System.out.println(Thread.currentThread().getName() + ". Bank added balance. Balance is " + balance);
            } finally {
                condition.signalAll();
                lock.unlock();
            }
        }
    }
}
