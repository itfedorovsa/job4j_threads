package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());
    private static final String SUBJECT = "Notification {%s} to email {%s}";
    private static final String BODY = "Add a new event to {%s}";

    public void emailTo(User user) {
        pool.submit(() -> {
            String username = user.getUsername();
            String email = user.getEmail();
            String subject = String.format(SUBJECT, username, user.getEmail());
            String body = String.format(BODY, username);
            send(subject, body, email);
        });

    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }

}
