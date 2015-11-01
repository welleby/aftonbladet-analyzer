import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.sql.Date;

/**
 * Created by kerling on 01/11/15.
 */
public class Article {

    private static final String ARTICLE_DIV = "abBodyText clearfix abJsBodyText";
    private String title, body, author;
    private int wordCount;

    private Date timestamp;

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Article(Document document) {
        parseDocument(document);
    }

    private void parseDocument(Document doc) {
        Elements abLeadText = doc.body().getElementsByAttributeValueContaining("class", "abBodyText clearfix abJsBodyText");
        body = abLeadText.text();
        String trim = body.trim();
        wordCount = trim.split("\\s+").length;
        System.out.println(body);
        System.out.println(wordCount);
    }
}
