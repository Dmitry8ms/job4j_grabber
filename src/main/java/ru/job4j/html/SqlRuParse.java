package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.PsqlStore;
import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;
import ru.job4j.quartz.LoadProps;

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
        try (var psql = new PsqlStore(LoadProps.getLoadedPropsFrom("grabber.properties"))) {
            SqlRuParse sqlRuParse = new SqlRuParse(parser);
            String httpStr = "https://www.sql.ru/forum/job-offers/";
            List<Post> posts = sqlRuParse.list(httpStr);
            for (var post : posts) {
                psql.save(post);
            }
            System.out.println(psql.findById(266));
            for (var post : psql.getAll()) {
                System.out.println(post);
            }
        }
    }

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new LinkedList<>();
        for (int i = 1; i <= 5; i++) {
            String sendLink = link + i;
            Document doc = null;
            try {
                doc = Jsoup.connect(sendLink).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    public Post detail(String vacancyLink) {
        Post post = new Post();
        Document vacancyDoc = null;
        try {
            vacancyDoc = Jsoup.connect(vacancyLink).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = vacancyDoc.select(".messageHeader").get(0).text();
        title = title.replace(" [new]", "");
        String grabbedDate = vacancyDoc.select(".msgFooter").get(0).ownText();
        grabbedDate = grabbedDate.substring(0, grabbedDate.indexOf(" []"));
        LocalDateTime created = dateTimeParser.parse(grabbedDate);
        String descr = vacancyDoc.select(".msgBody").get(1).text();
        post.setTitle(title);
        post.setLink(vacancyLink);
        post.setDescription(descr);
        post.setCreated(created);
        return post;
    }
}
