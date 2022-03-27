package ru.job4j.kiss;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MaxMin {
    public <T> T max(List<T> list, Comparator<T> comparator) {
        T max = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            T toCompare = list.get(i);
            if (comparator.compare(max, toCompare) < 0) {
                max = toCompare;
            }
        }
        return max;
    }

    public <T> T min(List<T> list, Comparator<T> comparator) {
        var minComparator = comparator.reversed();
        return max(list, minComparator);
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
