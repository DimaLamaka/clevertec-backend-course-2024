package ru.clevertec.multithreading.step8.example2;


public class AccountThread extends Thread {
    private final Account from;
    private final Account to;

    public AccountThread(Account from, Account to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        for (int i = 0; i < 2000; i++) {
            lockAccounts();
            try {
                if (from.takeOff(10)) {
                    to.add(10);
                }
            } finally {
                from.getLock().unlock();
                to.getLock().unlock();
            }
        }
    }

    private void lockAccounts() {
        while (true) {
            boolean fromLock = from.getLock().tryLock();
            boolean toLock = to.getLock().tryLock();
            if (fromLock && toLock) {
                break;
            }
            if (fromLock) {
                from.getLock().unlock();
            }
            if (toLock) {
                to.getLock().unlock();
            }
        }
    }
}
