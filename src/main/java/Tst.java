import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

/**
 * Created by kerling on 01/11/15.
 */
public class Tst {

    private static String baseUrl = "http://www.aftonbladet.se/article";


    public static void main(String[] args) throws IOException {
        File workingUrls = new File("workingsUrls.txt");
        for (int i = 447; i < 21684046; i++) {
            String url = baseUrl + i + ".ab";
            Connection conn = Jsoup.connect(url);
            conn.method(Connection.Method.HEAD);
            conn.followRedirects(true);
            try {
                Document document = conn.get();
                final int statusCode = conn.response().statusCode();
                System.out.println(url);
                FileUtils.writeStringToFile(workingUrls, url + "\n", true);
                
            } catch (HttpStatusException e) {
                System.err.println(e.getStatusCode() + " on " + url);
            }
        }
    }
}
