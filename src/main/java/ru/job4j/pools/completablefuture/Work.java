package ru.job4j.pools.completablefuture;

import java.util.concurrent.TimeUnit;

public class Work {
    public static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("You: I am working");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }
}
