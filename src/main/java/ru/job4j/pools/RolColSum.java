package ru.job4j.pools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        for (int index = 0; index < size; index++) {
            sums[index] = new RolColSum.Sums();
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sums[i].rowSum += matrix[i][j];
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sums[i].colSum += matrix[j][i];
            }
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws Exception {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        for (int index = 0; index < size; index++) {
            sums[index] = new RolColSum.Sums();
        }
        for (int index = 0; index < size; index++) {
            CompletableFuture<Map<String, Integer>> sum = getRowAndColumnSum(matrix, size, index);
            sums[index].rowSum = sum.get().get("row");
            sums[index].colSum = sum.get().get("col");
        }
        return sums;
    }

    public static CompletableFuture<Map<String, Integer>> getRowAndColumnSum(int[][] matrix, int size, int position) {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, Integer> map = new HashMap<>();
            int rowSum = 0;
            for (int index = 0; index < size; index++) {
                rowSum += matrix[position][index];
            }
            int colSum = 0;
            for (int index = 0; index < size; index++) {
                colSum += matrix[index][position];
            }
            map.putIfAbsent("row", rowSum);
            map.putIfAbsent("col", colSum);
            return map;
        });
    }

    public static void main(String[] args) throws Exception {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(Arrays.toString(sum(matrix)));
        System.out.println(Arrays.toString(asyncSum(matrix)));
    }

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }
    }
}
