package ru.job4j.concurrent;

public class ThreadState {

    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println("First thread name: " + Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println("Second thread name: " + Thread.currentThread().getName())
        );
        System.out.println("First thread condition: ");
        System.out.println(first.getState());
        first.start();
        while (first.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
        }
        System.out.println(first.getState());
        System.out.println("Second thread condition: ");
        System.out.println(second.getState());
        second.start();
        while (second.getState() != Thread.State.TERMINATED) {
            System.out.println(second.getState());
        }
        System.out.println(second.getState());
        System.out.println(Thread.currentThread().getName() + " is terminated");
    }
}
