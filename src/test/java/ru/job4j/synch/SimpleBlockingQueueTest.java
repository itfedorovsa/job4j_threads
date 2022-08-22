package ru.job4j.synch;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.*;

class SimpleBlockingQueueTest {

    @Test
    void whenAddFiveElementsAndPollThreeElements() throws InterruptedException {
        var queue = new SimpleBlockingQueue<Integer>(3);
        var producer = new Thread(() -> {
            for (int index = 0; index < 5; index++) {
                try {
                    queue.offer(index);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        var consumer = new Thread(() -> {
            for (int index = 0; index < 2; index++) {
                try {
                    queue.poll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        consumer.start();
        producer.start();
        try {
            consumer.join();
            producer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        var check = new SimpleBlockingQueue<Integer>(3);
        check.offer(2);
        check.offer(3);
        check.offer(4);
        List<Integer> rsl = new ArrayList<>();
        rsl.add(check.poll());
        rsl.add(check.poll());
        rsl.add(check.poll());
        assertThat(rsl).isEqualTo(List.of(2, 3, 4));
    }

    @Test
    void whenTwoConsumers() throws InterruptedException {
        var queue = new SimpleBlockingQueue<Integer>(3);
        var producer = new Thread(() -> {
            for (int index = 0; index < 7; index++) {
                try {
                    queue.offer(index);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        var consumer1 = new Thread(() -> {
            for (int index = 0; index < 2; index++) {
                try {
                    queue.poll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        var consumer2 = new Thread(() -> {
            for (int index = 0; index < 2; index++) {
                try {
                    queue.poll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        consumer1.start();
        consumer2.start();
        producer.start();
        try {
            consumer1.join();
            consumer2.join();
            producer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        var check = new SimpleBlockingQueue<Integer>(3);
        check.offer(4);
        check.offer(5);
        check.offer(6);
        List<Integer> rsl = new ArrayList<>();
        rsl.add(check.poll());
        rsl.add(check.poll());
        rsl.add(check.poll());
        assertThat(rsl).isEqualTo(List.of(4, 5, 6));
    }
}