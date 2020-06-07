package com.hxq.juc;

import java.util.concurrent.*;

public class SynchronizedTest {

    //交替打印A-z和1-26
    public static void main(String[] args) throws InterruptedException {
        String[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
        int max = 26;

        Object lock = new Object();

        Thread azThread = new Thread(() -> {
            for (int i = 0; i < max; i++) {
                synchronized (lock) {
                    System.out.print(a[i] + " ");
                    lock.notify();
                    try {
                        if (i < max - 1) {
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sleep();
                }

            }
        });

        Thread numThread = new Thread(() -> {
            for (int i = 0; i < max; i++) {
                synchronized (lock) {
                    System.out.print(i + 1 + " ");
                    lock.notify();
                    try {
                        if (i < max - 1) {
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sleep();
                }

            }
        });

        azThread.start();
        numThread.start();
        azThread.join();
        numThread.join();
        System.out.println("finished----------------");
    }

    public static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(Math.round(Math.random() * 1));
        } catch (InterruptedException e) {
        }
    }
}
