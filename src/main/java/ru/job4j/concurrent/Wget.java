package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        int index = 0;
        while (index <= 100) {
            System.out.print("\rLoading : " + index + "%");
            index++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
