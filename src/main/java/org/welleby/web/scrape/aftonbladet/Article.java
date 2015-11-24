package org.welleby.web.scrape.aftonbladet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by kerling on 01/11/15.
 */
public class Article {
    private static final String ARTICLE_DIV = "abBodyText clearfix abJsBodyText";
    private static final String AUTHOR_DIV = "abByline";
    private static final String TIMESTAMP_ATTR = "ABse.page.articlePublishedDateTime";
    private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final Logger logger = LogManager.getLogger(Article.class);

    private String title, body, author;

    private int wordCount;

    private Timestamp timestamp;

    public int getWordCount() {
        return wordCount;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Article(Document document) throws ParseException {
        parseDocument(document);
    }

    public String toString() {
        return String.format("Title: %s, Author: %s, Timestamp: %s, WordCount: %s ", title, author, timestamp, wordCount);
    }

    private void parseDocument(Document doc) throws ParseException {
        body = doc.body().getElementsByAttributeValueContaining("class", ARTICLE_DIV).text();
        author = doc.body().getElementsByAttributeValueContaining("class", AUTHOR_DIV).text();
        int index = doc.title().indexOf('|');
        if(index < 2) {
            logger.debug(doc.title());
            throw new ParseException("Title too short",0);
        }
        title = doc.title().substring(0, index).trim();
        wordCount = body.trim().split("\\s+").length;
        String s = doc.head().html();
        index = s.indexOf(TIMESTAMP_ATTR);
        try {
            java.util.Date d = new SimpleDateFormat(DATETIME_FORMAT).parse(s.substring(index + 38, index + 57));
            timestamp = new Timestamp(d.getTime());
        } catch (ParseException e) {
            timestamp = new Timestamp(1);
            logger.debug(s.substring(index + 38, index + 57) + " Couldn't be parsed to a date");
        }

        logger.debug(this.toString());
    }
}
