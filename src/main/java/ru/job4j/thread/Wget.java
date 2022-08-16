package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("downloaded.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long timestamp = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long now = System.currentTimeMillis();
                if (timestamp + speed >= now) {
                    Thread.sleep(timestamp + speed - now);
                }
                timestamp = System.currentTimeMillis();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        if (args.length != 2) {
            throw new IllegalArgumentException("Arguments must looks like \"URL speed\"");
        }
        try {
            new URL(url).toURI();
            Integer.parseInt(args[1]);
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Speed must be a number!");
        }
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

}
