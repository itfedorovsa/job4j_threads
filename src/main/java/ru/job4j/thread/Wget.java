package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String output;
    private final long interval = 1000;

    public Wget(String url, int speed, String output) {
        this.url = url;
        this.speed = speed;
        this.output = output;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(output)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadedData = 0;
            long timestamp = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadedData += bytesRead;
                if (downloadedData >= speed) {
                    long now = System.currentTimeMillis();
                    long timeDiff = now - timestamp;
                    if (timeDiff < interval) {
                        Thread.sleep(interval - timeDiff);
                        downloadedData = 0;
                        timestamp = System.currentTimeMillis();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        String output = args[2];
        try {
            new URL(url).toURI();
            Integer.parseInt(args[1]);
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Speed must be a number!");
        }
        Matcher matcher = Pattern.compile("\\w+.[a-z]{2,5}").matcher(output);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Output filename");
        }
        if (args.length != 3) {
            throw new IllegalArgumentException("Arguments must looks like \"URL speed output_name\"");
        }
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed, output));
        wget.start();
        wget.join();
    }

}
