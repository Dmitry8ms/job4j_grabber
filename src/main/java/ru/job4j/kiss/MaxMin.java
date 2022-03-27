package ru.job4j.kiss;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntPredicate;

public class MaxMin {

    public static final IntPredicate MAX = (i) -> i < 0;
    public static final IntPredicate MIN = (i) -> i > 0;

    public static <T> T ext(List<T> list, Comparator<T> comparator, IntPredicate condition) {
        T extr = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            T toCompare = list.get(i);
            if (condition.test(comparator.compare(extr, toCompare))) {
                extr = toCompare;
            }
        }
        return extr;
    }

    public <T> T max(List<T> list, Comparator<T> comparator) {
        return ext(list, comparator, MAX);
    }

    public <T> T min(List<T> list, Comparator<T> comparator) {
        return ext(list, comparator, MIN);
    }

    public static void main(String[] args) {
        var maxMin = new MaxMin();
        Comparator<Integer> compare = (i1, i2) -> i1 - i2;
        var testList = new ArrayList<Integer>(10);
        for (int i = 0; i < 10; i++) {
            testList.add(i, (int) (Math.random() * 9));
        }
        System.out.println(testList);
        System.out.println("max = " + maxMin.max(testList, compare));
        System.out.println("min = " + maxMin.min(testList, compare));
    }
}
