package ru.clevertec.multithreading.step13;

import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class PhaserExample {
    public static void main(String[] args) {
        int phaseCount = 11; // Общее количество фаз
        Phaser phaser = new Phaser(1); // Создаем Phaser с одним участником — основным потоком

        // Создаем и запускаем несколько рабочих потоков
        for (int i = 1; i <= 5; i++) {
            int startPhase = ThreadLocalRandom.current().nextInt(phaseCount / 2) + 1;
            int endPhase = ThreadLocalRandom.current().nextInt(5) + startPhase + 1;

            new Thread(new Worker(phaser, "Worker-" + i, startPhase, endPhase)).start();
        }

        // Основной поток ждет завершения всех фаз
        for (int phase = 0; phase < phaseCount; phase++) {
            System.out.println("PHASE >>>> " + phase);
            phaser.arriveAndAwaitAdvance();
            System.out.println("All threads have completed the phase № " + phase);
        }

        // Завершение работы основного потока
        phaser.arriveAndDeregister();
        System.out.println("Main thread has completed.");
    }

    static class Worker implements Runnable {
        private final Phaser phaser;
        private final String name;
        private final int startPhase;
        private final int endPhase;

        public Worker(Phaser phaser, String name, int startPhase, int endPhase) {
            this.phaser = phaser;
            this.name = name;
            this.startPhase = startPhase;
            this.endPhase = endPhase;
            phaser.register();
            System.out.println(name + " >>>>> REGISTERED, FROM: " + startPhase + ", TO: " + endPhase);
        }

        @Override
        public void run() {
            try {
                // Ждем пока фазы начнутся
                while (phaser.getPhase() < startPhase) {
                    phaser.arriveAndAwaitAdvance();
                }

                for (int phase = startPhase; phase <= endPhase; phase++) {
                    System.out.println(name + " >>>>> STARTED the phase №" + phase);
                    doWork();
                    System.out.println(name + " >>>>> COMPLETED the phase №" + phase);
                    phaser.arriveAndAwaitAdvance();
                    TimeUnit.SECONDS.sleep(1);// Уведомляем о завершении текущей фазы и ждем
                }

            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            } finally {
                // Завершение работы потока
                phaser.arriveAndDeregister();
                System.out.println(name + " >>>>> DEREGISTER");
            }
        }

        private void doWork() {
            try {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5) + 2); // Имитация выполнения работы
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
