package com.hxq.juc;

public class Juc {


    public static void main(String[] args) {
        final int MAX_CNT = THREAD_AMOUNT * REPEAT_AMOUNT;
        int cnt = 0;
        Juc juc = new Juc();
        do {
            try {
                cnt = juc.m();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            System.out.println(cnt);
        } while (cnt == MAX_CNT);

    }

    private static final int THREAD_AMOUNT = 1_000;
    private static final int REPEAT_AMOUNT = 10_000;

    public int m() throws InterruptedException {
        TestThread testThread = new TestThread();

        Thread[] threads = new Thread[THREAD_AMOUNT];
        for (int i = 0; i < THREAD_AMOUNT; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < REPEAT_AMOUNT; j++) {
                    testThread.increase();
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return testThread.count;
    }

    public static class TestThread {
        private  int count = 0;

          void increase() {
            count++;
        }
    }
}
