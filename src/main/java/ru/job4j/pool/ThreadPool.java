package ru.job4j.pool;

import ru.job4j.synch.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final int threadsNumber = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(threadsNumber);

    public ThreadPool() {
        for (int index = 0; index < threadsNumber; index++) {
            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

}
