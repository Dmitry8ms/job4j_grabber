package ru.job4j.html;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.format.DateTimeFormatter;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "dd.MM.yyyy HH:mm");
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element parent = td.parent();
            System.out.println(parent.child(1).attr("href"));
            System.out.println(parent.child(1).text());
            String date = parent.child(5).text();
            System.out.println("Дата: " + date);
            if (date != null) {
                System.out.println("LocaleDateTime: "
                        + formatter.format(new SqlRuDateTimeParser().parse(date)));
            }

        }
    }
}
