package ru.job4j.gc;

public class User {
    private static long counter;
    private long number = 0;
    public User(Long n) {
        this.number = n;
        counter = n;
    }

    @Override
    protected void finalize() throws Throwable {
        GCDemo.info();
        System.out.printf("Removed user #%d, counter = %d %n", number, counter);
    }
}
