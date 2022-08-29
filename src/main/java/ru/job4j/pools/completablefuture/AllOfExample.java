package ru.job4j.pools.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AllOfExample {
    public static CompletableFuture<Void> washHands(String name) {
        return CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + ", washes his/her hands");
        });
    }

    public static void allOfExample() throws Exception {
        CompletableFuture<Void> all = CompletableFuture.allOf(
                washHands("Mom"), washHands("Dad"),
                washHands("John"), washHands("Jade")
        );
        TimeUnit.SECONDS.sleep(3);
    }

    public static void main(String[] args) throws Exception {
        allOfExample();
    }
}
