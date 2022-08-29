package ru.job4j.pools.completablefuture;

import java.util.concurrent.CompletableFuture;

public class ThenAcceptExample {
    public static void thenAcceptExample() throws Exception {
        CompletableFuture<String> bm = SupplyAsyncExample.buyProduct("milk");
        bm.thenAccept((product) -> System.out.println("Son: I put the " + product + " in the fridge."));
        Work.iWork();
        System.out.println("Purchased: " + bm.get());
    }

    public static void main(String[] args) throws Exception {
        thenAcceptExample();
    }
}
