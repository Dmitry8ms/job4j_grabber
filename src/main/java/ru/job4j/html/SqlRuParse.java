package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        int depth = 5;
        List<Post> posts = grabPostsFromSQLru(depth);
        for (var post : posts) {
            System.out.println(post);
        }

    }

    private static List<Post> grabPostsFromSQLru(int depth) throws IOException {
        List<Post> posts = new LinkedList<>();
        LocalDateTime localDateTime = null;
        for (int i = 1; i <= depth; i++) {
            String httpStr = "https://www.sql.ru/forum/job-offers/" + i;
            Document doc = Jsoup.connect(httpStr).get();
            Elements rows = doc.select(".postslisttopic");
            for (Element row : rows) {
                Post post = new Post();
                Element parent = row.parent();
                String vacancyLink = row.child(0).attr("href");
                String title = parent.child(1).text();
                String date = parent.child(5).text();
                if (date != null) {
                    localDateTime = new SqlRuDateTimeParser().parse(date);
                }
                Document vacancyDoc = Jsoup.connect(vacancyLink).get();
                Elements elements = vacancyDoc.select(".msgTable");
                String descr = elements.get(0).child(0).child(1).child(1).text();
                post.setTitle(title);
                post.setLink(vacancyLink);
                post.setDescription(descr);
                post.setCreated(localDateTime);
                posts.add(post);
            }
        }
        return posts;
    }
}
