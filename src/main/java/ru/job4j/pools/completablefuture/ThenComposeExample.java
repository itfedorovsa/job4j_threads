package ru.job4j.pools.completablefuture;

import java.util.concurrent.CompletableFuture;

public class ThenComposeExample {
    public static void thenComposeExample() throws Exception {
        CompletableFuture<String> result = RunAsyncExample.goToTrash().thenCompose(a -> SupplyAsyncExample.buyProduct("milk"));
        result.get();
    }

    public static void main(String[] args) throws Exception {
        thenComposeExample();
    }
}
