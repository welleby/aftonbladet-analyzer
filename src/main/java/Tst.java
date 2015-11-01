import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.*;
/**
 * Created by kerling on 01/11/15.
 */
public class Tst {

    private static String baseUrl = "http://www.aftonbladet.se/article";

    private static List<String> workingUrls = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
        IOUtils.toString();
        for (int i = 447; i < 21684046; i++) {
            String url = baseUrl + i + ".ab";
            Connection conn = Jsoup.connect(url);
            conn.method(Connection.Method.HEAD);
            conn.followRedirects(true);
            try {
                Document document = conn.get();
                final int statusCode = conn.response().statusCode();
                System.out.println(url);
                workingUrls.add(url);
            } catch (HttpStatusException e) {
                System.err.println(e.getStatusCode() + " on " + url);
            }
        }
    }
}
