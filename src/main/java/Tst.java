import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by kerling on 01/11/15.
 */
public class Tst {

    private static String baseUrl = "http://www.aftonbladet.se/article";


    public static void main(String[] args) throws IOException {
        File workingUrls = new File("workingsUrls.txt");
        int currentId;
        try {
            String lastUrl = new ReversedLinesFileReader(workingUrls).readLine();
            currentId = Integer.parseInt(lastUrl.replaceAll("[^-?0-9]+", ""));
        } catch (FileNotFoundException exception) {
            currentId = 446;
        }

        for (int i = currentId+1; i < 21684046; i++) {
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
