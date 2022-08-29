package ru.job4j.pools.completablefuture;

import java.util.concurrent.CompletableFuture;

public class ThenApplyExample {
    public static void thenApplyExample() throws Exception {
        CompletableFuture<String> bm = SupplyAsyncExample.buyProduct("milk")
                .thenApply((product) -> "Son: I poured " + product + " into your mug. Hold.");
        Work.iWork();
        System.out.println(bm.get());
    }

    public static void main(String[] args) throws Exception {
        thenApplyExample();
    }
}
