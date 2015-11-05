package org.welleby.web.scrape.aftonbladet;

import org.apache.commons.io.input.ReversedLinesFileReader;
import org.javalite.activejdbc.Base;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.welleby.web.scrape.aftonbladet.activeJDBC.ActiveJDBC;

import java.io.File;
import java.io.IOException;

/**
 * Created by kerling on 05/11/15.
 */
public class ArticleScraper {
    private static final String URL_FILE = "workingUrls.txt";


    public static void main(String[] args) {
        Base.open("org.sqlite.JDBC", "jdbc:sqlite:test.db", "", "");

        File workingUrlsFile = new File(URL_FILE);
        try (ReversedLinesFileReader fileReader = new ReversedLinesFileReader(workingUrlsFile)) {
            String lastUrl;
            while ((lastUrl = fileReader.readLine()) != null) {
                Connection conn = Jsoup.connect(lastUrl);
                Document document = conn.get();
                Article article = new Article(document);
                ActiveJDBC.addArticleToDB(article);
            }
        } catch (IOException exception) {
        }
    }

}
