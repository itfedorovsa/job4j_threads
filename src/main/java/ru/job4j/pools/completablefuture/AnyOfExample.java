package ru.job4j.pools.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AnyOfExample {
    public static CompletableFuture<String> whoWashHands(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name + " washes his/her hands";
        });
    }

    public static void anyOfExample() throws Exception {
        CompletableFuture<Object> first = CompletableFuture.anyOf(
                whoWashHands("Dad"), whoWashHands("Mom"),
                whoWashHands("John"), whoWashHands("Jade")
        );
        System.out.println("Who is washing their hands now?");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(first.get());
    }

    public static void main(String[] args) throws Exception {
        anyOfExample();
    }
}
