package ru.job4j.benchmark;

public class SimpleBench {

    public static void main(String[] args) {
        int n = 43;
        System.out.println("Fibonacci iteration:");
        long start = System.nanoTime();
        int fibN = fib(n);
        System.out.printf("result = %d is: %d \n", n, fibN);
        long elapsedTime = System.nanoTime() - start;
        System.out.printf("Nanoseconds: %d ns\n", elapsedTime);
    }
    static int fib(int n) {
        if (n < 2) {
            return 1;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }
}
