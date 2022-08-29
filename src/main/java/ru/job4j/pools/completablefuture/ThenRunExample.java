package ru.job4j.pools.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ThenRunExample {
    public static void thenRunExample() throws Exception {
        CompletableFuture<Void> gtt = RunAsyncExample.goToTrash();
        gtt.thenRun(() -> {
            int count = 0;
            while (count < 3) {
                System.out.println("Son: I'm washing my hands.");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
            System.out.println("Son: I washed my hands.");
        });
        Work.iWork();
    }

    public static void main(String[] args) throws Exception {
        thenRunExample();
    }
}
