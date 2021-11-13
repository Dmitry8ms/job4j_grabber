package ru.job4j.quartz;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProps {
    public static Properties getLoadedPropsFrom(String fileName) {
        Properties prs = null;
        try (InputStream in = AlertRabbit.class.getClassLoader()
                .getResourceAsStream(fileName)) {
            prs = new Properties();
            prs.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prs;
    }
}
