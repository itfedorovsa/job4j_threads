package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    void whenCoherentSum() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        List<Integer> expected = List.of(6, 12, 15, 15, 24, 18);
        List<Integer> rsl = new ArrayList<>();
        RolColSum.Sums[] sum = RolColSum.sum(matrix);
        for (RolColSum.Sums el : sum) {
            rsl.add(el.getRowSum());
            rsl.add(el.getColSum());
        }
        assertThat(rsl).isEqualTo(expected);
    }

    @Test
    void whenAsyncSum() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        List<Integer> expected = List.of(6, 12, 15, 15, 24, 18);
        List<Integer> rsl = new ArrayList<>();
        try {
            RolColSum.Sums[] sum = RolColSum.asyncSum(matrix);
            for (RolColSum.Sums el : sum) {
                rsl.add(el.getRowSum());
                rsl.add(el.getColSum());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(rsl).isEqualTo(expected);
    }
}