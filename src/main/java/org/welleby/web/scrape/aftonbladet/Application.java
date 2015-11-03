package org.welleby.web.scrape.aftonbladet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.apache.logging.log4j.*;

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
    private static final String URL_FILE = "workingUrls.txt";
    private static Logger logger = LogManager.getLogger(Application.class);


    public static void main(String[] args) throws IOException {
        File workingUrls = new File(URL_FILE);
        ReversedLinesFileReader fileReader = new ReversedLinesFileReader(workingUrls);
        int currentId;
        try {
        	logger.debug("Reading file "+URL_FILE+" to pick up where we left of.");
            String lastUrl = fileReader.readLine();
            
            currentId = Integer.parseInt(lastUrl.replaceAll("[^-?0-9]+", ""));
            logger.debug("Starting at "+currentId);
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
            	logger.info("Found url: "+url);
                FileUtils.writeStringToFile(workingUrls, url + "\n", true);
            }else {
            	logger.info(responseCode+" on url: "+url);
            }
        }
    }
}
