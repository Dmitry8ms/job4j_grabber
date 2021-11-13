package ru.job4j.html;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.format.DateTimeFormatter;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements rows = doc.select(".postslisttopic");
        for (Element row : rows) {
            Element parent = row.parent();
            System.out.println(row.child(0).attr("href"));
            System.out.println(parent.child(1).text());
            String date = parent.child(5).text();
            if (date != null) {
                System.out.println(new SqlRuDateTimeParser().parse(date));
            }

        }
    }
}
