package ru.job4j.grabber.utils;

import ru.job4j.grabber.Grabber;
import ru.job4j.html.SqlRuParse;
import ru.job4j.quartz.AlertRabbit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProps {
    public static Properties getLoadedPropsFrom(String fileName) {
        Properties prs = null;
        try (InputStream in = Grabber.class.getClassLoader()
                .getResourceAsStream(fileName)) {
            prs = new Properties();
            prs.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prs;
    }
}
