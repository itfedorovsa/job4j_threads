package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class ParallelFindTest {

    @Test
    void whenDifferentTypes() {
        Integer[] array = new Integer[]{9, 8, 7, 6, 5};
        String elem = "D";
        Integer expected = -1;
        assertThat(ParallelFind.find(array, elem)).isEqualTo(expected);
    }

    @Test
    void whenLinearSearch() {
        Integer[] array = new Integer[]{9, 8, 7, 6, 5};
        Integer elem = 6;
        Integer expected = 3;
        assertThat(ParallelFind.find(array, elem)).isEqualTo(expected);
    }

    @Test
    void whenRecursiveSearch1() {
        Integer[] array = IntStream.range(0, 30).boxed().toArray(Integer[]::new);
        Integer elem = 15;
        Integer expected = 15;
        assertThat(ParallelFind.find(array, elem)).isEqualTo(expected);
    }

    @Test
    void whenRecursiveSearch2() {
        Integer[] array = IntStream.range(0, 100).boxed().toArray(Integer[]::new);
        Integer elem = 65;
        Integer expected = 65;
        assertThat(ParallelFind.find(array, elem)).isEqualTo(expected);
    }

    @Test
    void whenElementNotFound() {
        Integer[] array = new Integer[]{9, 8, 7, 6, 5};
        Integer elem = 2;
        Integer expected = -1;
        assertThat(ParallelFind.find(array, elem)).isEqualTo(expected);
    }
}