package ru.job4j.kiss;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class MaxMinTest {

    @Test
    public void max() {
        var maxMin = new MaxMin();
        Comparator<Integer> compare = (i1, i2) -> i1 - i2;
        List<Integer> testList = Arrays.asList(3, 3, 0, 2);
        assertSame(Integer.valueOf(3), maxMin.max(testList, compare));
    }

    @Test
    public void min() {
        var maxMin = new MaxMin();
        Comparator<Integer> compare = (i1, i2) -> i1 - i2;
        List<Integer> testList = Arrays.asList(3, 3, 0, 2);
        assertSame(Integer.valueOf(0), maxMin.min(testList, compare));
    }
}