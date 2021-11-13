package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= 5; i++) {
            String httpStr = "https://www.sql.ru/forum/job-offers/" + i;
            Document doc = Jsoup.connect(httpStr).get();
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
}
