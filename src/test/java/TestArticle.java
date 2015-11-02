import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.welleby.web.scrape.aftonbladet.Article;

import java.io.IOException;

/**
 * Created by kerling on 01/11/15.
 */
public class TestArticle {

    public static void main(String[] args) {

        Connection conn = Jsoup.connect("http://www.aftonbladet.se/nyheter/article21685099.ab");
        try {
            Article article = new Article(conn.get());
            System.out.println("finished");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
