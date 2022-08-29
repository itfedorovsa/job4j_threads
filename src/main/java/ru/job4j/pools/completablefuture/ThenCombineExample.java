package ru.job4j.pools.completablefuture;

import java.util.concurrent.CompletableFuture;

public class ThenCombineExample {
    public static void thenCombineExample() throws Exception {
        CompletableFuture<String> result = SupplyAsyncExample.buyProduct("milk")
                .thenCombine(SupplyAsyncExample.buyProduct("bread"), (r1, r2) -> "Purchased: " + r1 + " and " + r2);
        Work.iWork();
        System.out.println(result.get());
    }

    public static void main(String[] args) throws Exception {
        thenCombineExample();
    }
}
