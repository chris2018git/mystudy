package com.hxq.juc;

import java.util.concurrent.*;

public class ExceptionSynchronizedTest {
    private int count = 0;

    synchronized void m() {
        System.out.println(Thread.currentThread().getName() + " start!");
        while (true) {
            count++;
            System.out.println(Thread.currentThread().getName() + " " + count);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (count == 5) {
                int i = 1 / 0;
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {


        ExceptionSynchronizedTest test = new ExceptionSynchronizedTest();
        new Thread(() -> {
            test.m();
        }, "Thread-01").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            test.m();
        }, "Thread-02").start();


    }
}
