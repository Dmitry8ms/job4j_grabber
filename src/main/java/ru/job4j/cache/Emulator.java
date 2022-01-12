package ru.job4j.cache;

import java.util.Scanner;

public class Emulator {
    public static void main(String[] args) {
        System.out.println("Please enter directory: ");
        Scanner console = new Scanner(System.in);
        String cachingDir = console.nextLine();
        AbstractCache<String, String> fileCache = new DirFileCache(cachingDir);
        String fileName;
        do {
            System.out.println("Please enter file to cache, or q for exit: ");
            fileName = console.nextLine();
            if (!fileName.equals("q")) {
                String contents = fileCache.load(fileName);
                System.out.printf("This is cache contents: %n%s%n", contents);
            }
        }
        while (!fileName.equals("q"));
    }
}
