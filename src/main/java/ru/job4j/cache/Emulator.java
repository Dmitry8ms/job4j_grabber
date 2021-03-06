package ru.job4j.cache;

import java.util.Scanner;

public class Emulator {
    public static void main(String[] args) {
        System.out.println("Please enter directory: ");
        Scanner console = new Scanner(System.in);
        String cachingDir = console.nextLine();
        AbstractCache<String, String> fileCache = new DirFileCache(cachingDir);
        String key;
        do {
            System.out.println("Please enter file to cache, or q for exit: ");
            key = console.nextLine();
            if (!key.equals("q")) {
                String contents = fileCache.load(key);
                fileCache.put(key, contents);
                System.out.printf("This is file contents: %n%s%n", contents);
                System.out.println("____________________");
                System.out.printf("This is cache contents: %n%s%n", fileCache.get(key));
            }
        }
        while (!key.equals("q"));
    }
}
