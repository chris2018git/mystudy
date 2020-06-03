package com.hxq.juc;

public class Juc {
    private int count = 0;

    Runnable runnable = () -> {
        System.out.println("count=" + count++);
    };

    public static class SynThread extends Thread {
        public SynThread(int num) {

        }

        @Override
        public void run() {
            super.run();
        }
    }

}
