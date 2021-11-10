package ru.job4j.quartz;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LoadProps {
    public static Properties getLoadedPropsFrom(String fileName) {
        Properties prs = new Properties();
        File file = new File(fileName);
        try (var reader = new FileReader(file)) {
            prs.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prs;
    }
}
