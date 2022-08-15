package ru.job4j.concurrent;

public class ThreadState {

    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println("First thread name: " + Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println("Second thread name: " + Thread.currentThread().getName())
        );
        System.out.println("First thread start condition: " + first.getState());
        System.out.println("Second thread start condition: " + second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println("First thread condition: " + first.getState());
            System.out.println("Second thread condition: " + second.getState());
        }
        System.out.println("First thread final condition: " + first.getState());
        System.out.println("Second thread final condition: " + second.getState());
        System.out.println(Thread.currentThread().getName() + " is terminated");
    }
}
