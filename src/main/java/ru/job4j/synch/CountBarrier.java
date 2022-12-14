package ru.job4j.synch;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier barrier = new CountBarrier(3);
        Thread waiter1 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " still sleeping");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " finally started");
                }, "Waiter1"
        );
        Thread waiter2 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " still sleeping too");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " finally started too");
                }, "Waiter2"
        );
        Thread increaser1 = new Thread(
                () -> {
                    barrier.count();
                    System.out.println(Thread.currentThread().getName() + " increases counter");
                }, "Increaser1"
        );
        Thread increaser2 = new Thread(
                () -> {
                    barrier.count();
                    System.out.println(Thread.currentThread().getName() + " increases counter");
                }, "Increaser2"
        );
        Thread increaser3 = new Thread(
                () -> {
                    barrier.count();
                    System.out.println(Thread.currentThread().getName() + " increases counter");
                }, "Increaser3"
        );
        waiter1.start();
        waiter2.start();
        increaser1.start();
        increaser2.start();
        increaser3.start();
    }
}
