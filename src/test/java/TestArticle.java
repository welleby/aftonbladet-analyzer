import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by kerling on 01/11/15.
 */
public class TestArticle {

    public static void main(String[] args) {

        Connection conn = Jsoup.connect("http://www.aftonbladet.se/article207081.ab");
        try {
            Article article = new Article(conn.get());
            System.out.println("finished");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
