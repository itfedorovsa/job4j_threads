package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int ref;
        int temp;
        do {
            ref = count.get();
            temp = ref;
        } while (!count.compareAndSet(ref, temp++));
    }

    public int get() {
        int rsl;
        int temp;
        do {
            temp = count.get();
            rsl = temp;
        } while (!count.compareAndSet(temp, rsl));
        return rsl;
    }
}
