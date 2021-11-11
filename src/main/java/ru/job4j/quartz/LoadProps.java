package ru.job4j.quartz;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LoadProps {
    public static Properties getLoadedPropsFrom(String fileName) {
        String propsFile = AlertRabbit.class.getClassLoader().getResource(fileName)
                .getFile();
        Properties prs = new Properties();
        File file = new File(propsFile);
        try (var reader = new FileReader(file)) {
            prs.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prs;
    }
}
