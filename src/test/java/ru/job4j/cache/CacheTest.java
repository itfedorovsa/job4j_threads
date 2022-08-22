package ru.job4j.cache;


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CacheTest {

    @Test
    void whenAdd() {
        Cache cache = new Cache();
        cache.add(new Base(1, 0));
        assertThat(cache.get(1)).isEqualTo(new Base(1, 0));
    }

    @Test
    void whenAddFailed() {
        Cache cache = new Cache();
        cache.add(new Base(1, 0));
        cache.add(new Base(1, 1));
        assertThat(cache.get(1)).isEqualTo(new Base(1, 0));
    }

    @Test
    void whenDelete() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        Base base2 = new Base(2, 0);
        cache.add(base1);
        cache.add(base2);
        cache.delete(base1);
        List<Base> rsl = cache.getAll();
        assertThat(rsl).isEqualTo(List.of(base2));
    }

    @Test
    void whenUpdate() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        base1.setName("John");
        Base base2 = new Base(1, 0);
        base2.setName("Jade");
        cache.add(base1);
        cache.update(base2);
        assertThat(cache.get(1).getName()).isEqualTo("Jade");
    }

    @Test
    void whenDifferentVersionsThenException() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        base1.setName("John");
        Base base2 = new Base(1, 1);
        base2.setName("Jade");
        cache.add(base1);
        assertThrows(OptimisticException.class, () -> cache.update(base2));
    }
}