package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFind<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T elem;

    public ParallelFind(T[] array, int from, int to, T elem) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.elem = elem;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            return findIndex();
        }
        int mid = (from + to) / 2;
        ParallelFind<T> leftFind = new ParallelFind<>(array, from, mid, elem);
        ParallelFind<T> rightFind = new ParallelFind<>(array, mid + 1, to, elem);
        leftFind.fork();
        rightFind.fork();
        int left = leftFind.join();
        int right = rightFind.join();
        return Math.max(left, right);
    }

    public int findIndex() {
        int rsl = -1;
        for (int index = from; index <= to; index++) {
            if (array[index].equals(elem)) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }

    public static <T> int find(T[] array, T elem) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelFind<>(array, 0, array.length - 1, elem));
    }

    public static void main(String[] args) {
        System.out.println(find(new Integer[]{9, 8, 7, 6, 5}, 8));
        System.out.println(find(new Integer[]{1, 2, 3, 4, 5}, 4));
    }

}
