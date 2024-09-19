package ru.clevertec.multithreading.step3;

import java.util.concurrent.TimeUnit;

public class VolatileExample {
    private boolean flag = false;

    public void volatileExampleStart() {
        Thread writerThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1L);
                flag = true;
                System.out.println("Flag is set to true");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread readerThread = new Thread(() -> {
            while (!flag) {
                int x = 5;
                x += 1;
//                System.out.println("Flag is false now");
            }
            System.out.println("Flag is true now");
        });

        writerThread.start();
        readerThread.start();
    }


    public static void main(String[] args) throws InterruptedException {
        VolatileExample volatileExample = new VolatileExample();
        volatileExample.volatileExampleStart();
    }
}
