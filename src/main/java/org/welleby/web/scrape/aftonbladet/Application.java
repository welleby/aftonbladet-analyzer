package org.welleby.web.scrape.aftonbladet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kerling on 01/11/15.
 */
public class Application {

    private static String baseUrl = "http://www.aftonbladet.se/article";


    public static void main(String[] args) throws IOException {
        File workingUrls = new File("workingsUrls.txt");
        ReversedLinesFileReader fileReader = new ReversedLinesFileReader(workingUrls);
        int currentId;
        try {
            String lastUrl = fileReader.readLine();
            
            currentId = Integer.parseInt(lastUrl.replaceAll("[^-?0-9]+", ""));
        } catch (FileNotFoundException exception) {
            currentId = 446;
        } finally{
        	fileReader.close();
        }

        for (int i = currentId + 1; i < 21684046; i++) {
            URL url = new URL(baseUrl + i + ".ab");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setInstanceFollowRedirects(false);
            int responseCode = connection.getResponseCode();
            if (responseCode == 301 || responseCode == 200) {
                System.out.println(url);
                FileUtils.writeStringToFile(workingUrls, url + "\n", true);
            }else {
                System.out.println(responseCode + " on " + url);
            }
        }
    }
}
