package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class SqlRuParse implements Parse {
    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }
    public static void main(String[] args) throws Exception {
        var parser = new SqlRuDateTimeParser();
        SqlRuParse sqlRuParse = new SqlRuParse(parser);
        String httpStr = "https://www.sql.ru/forum/job-offers/";
        List<Post> posts = sqlRuParse.list(httpStr);
        for (var post : posts) {
            System.out.println(post);
        }
    }

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> posts = new LinkedList<>();
        for (int i = 1; i <= 5; i++) {
            String sendLink = link + i;
            Document doc = Jsoup.connect(sendLink).get();
            Elements rows = doc.select(".postslisttopic");
            for (Element row : rows) {
                String vacancyLink = row.child(0).attr("href");
                Post post = detail(vacancyLink);
                posts.add(post);
            }
        }
        return posts;
    }

    @Override
    public Post detail(String vacancyLink) throws IOException {
        Post post = new Post();
        Document vacancyDoc = Jsoup.connect(vacancyLink).get();
        Elements elements = vacancyDoc.select(".msgTable");
        String title = elements.get(0).child(0).child(0).text();
        title = title.replace(" [new]", "");
        String grabbedDate = elements.get(0).child(0).child(2).child(0).ownText();
        grabbedDate = grabbedDate.substring(0, grabbedDate.indexOf(" []"));
        LocalDateTime created = dateTimeParser.parse(grabbedDate);
        String descr = elements.get(0).child(0).child(1).child(1).text();
        post.setTitle(title);
        post.setLink(vacancyLink);
        post.setDescription(descr);
        post.setCreated(created);
        return post;
    }
}
