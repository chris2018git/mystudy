package com.hxq.juc;

import java.util.*;
import java.util.concurrent.*;

/***
 *自定义线程安全容器类，实现put,take,getCount，实现2个生产者，10个消费者
 */
public class MyContainer<T> {
    private LinkedList<T> lists = new LinkedList<>();
    private static int MAX_SIZE = 10;
    private int count = 0;

    public void put(T t) {
        synchronized (this) {
            while (count == MAX_SIZE) {
                this.notifyAll();
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            lists.add(t);
            count++;
            System.out.println(Thread.currentThread().getName() + " put");
        }
        sleep();
    }

    public T take() {
        T t = null;
        synchronized (this) {
            while (count == 0) {
                this.notifyAll();
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            t = lists.removeFirst();
            count--;
            System.out.println(Thread.currentThread().getName() + " take");
        }

        sleep();
        return t;
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(Math.round(Math.random() * 1));
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) {
        MyContainer<Object> myContainer = new MyContainer<>();
        Thread[] producers = new Thread[2];
        Thread[] consumers = new Thread[10];

        for (int i = 0; i < producers.length; i++) {
            producers[i] = new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    myContainer.put(new Object());
                }
            }, "producer-" + i);
            producers[i].start();
        }
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    myContainer.take();
                }
            }, "consumer-" + i);
            consumers[i].start();
        }

    }
}
