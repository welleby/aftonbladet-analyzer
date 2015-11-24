package org.welleby.web.scrape.aftonbladet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javalite.activejdbc.Base;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.welleby.web.scrape.aftonbladet.activeJDBC.ActiveJDBC;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kerling on 05/11/15.
 */
public class ArticleScraper {
    private static final String URL_FILE = "workingUrls.txt";
    private static final String ERROR_URLS_FILE = "errorUrls.txt";
    private static final String SAVED_STATE_FILE = "state.txt"; //stores the last read line in the HUGE file
    private static final Logger logger = LogManager.getLogger(ArticleScraper.class);
    private static List<String> errorUrls = new ArrayList<String>();
    private static final int WRITE_BUFFER = 1000;
    private static int readLines = 0;

    public static void main(String[] args) {
        Base.open("org.sqlite.JDBC", "jdbc:sqlite:articles.db", "", "");

        File workingUrlsFile = new File(URL_FILE);
        int readLinesFromSavedState = 0;
        try (ReversedLinesFileReader fileReader = new ReversedLinesFileReader(workingUrlsFile)) {
            String savedStateContent = IOUtils.toString(new FileInputStream(SAVED_STATE_FILE));
            if (savedStateContent.length() > 1) {
                readLinesFromSavedState = Integer.parseInt(savedStateContent.replaceAll("[^-?0-9]+", ""));
            }
            String lastUrl;
            while ((lastUrl = fileReader.readLine()) != null) {
                if (readLinesFromSavedState > readLines) {
                    readLines++;
                    continue;
                }
                try {
                    logger.info("Url nbr "+ readLines + " " + lastUrl);
                    Connection conn = Jsoup.connect(lastUrl);
                    Document document = conn.get();
                    Article article = new Article(document);
                    ActiveJDBC.addArticleToDB(article);
                    if (errorUrls.size() >= WRITE_BUFFER) {
                        writeToFiles();
                        errorUrls.clear();
                    }
                } catch (HttpStatusException | ParseException e) {
                    logger.debug(e.getClass() + " " + e.getMessage());
                    errorUrls.add(lastUrl + " - " + e.getMessage());
                }
                readLines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writeToFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFiles() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String s : errorUrls) {
            sb.append(s + "\n");
        }
        FileUtils.writeStringToFile(new File(ERROR_URLS_FILE), sb.toString(), true);
        FileUtils.writeStringToFile(new File(SAVED_STATE_FILE), Integer.toString(readLines));
        logger.info("Wrote files to disk");
    }

}
