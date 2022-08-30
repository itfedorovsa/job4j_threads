package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        for (int index = 0; index < size; index++) {
            sums[index] = rowAndColumnSum(matrix, size, index);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws Exception {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int index = 0; index < size; index++) {
            final int position = index;
            CompletableFuture<Sums> el = CompletableFuture.supplyAsync(() -> rowAndColumnSum(matrix, size, position));
            futures.put(index, el);
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    private static Sums rowAndColumnSum(int[][] matrix, int size, int position) {
        int rowSum = 0;
        int colSum = 0;
        for (int index = 0; index < size; index++) {
            rowSum += matrix[position][index];
            colSum += matrix[index][position];
        }
        return new Sums(rowSum, colSum);
    }

}
