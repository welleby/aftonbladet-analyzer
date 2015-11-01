import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.sql.Date;

/**
 * Created by kerling on 01/11/15.
 */
public class Article {

    private static final String ARTICLE_DIV = "abMd6 abLefty abArticle ";
    private String title, body, author;

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
        Elements elementsByClass = doc.body().getElementsByAttributeValueContaining("class",ARTICLE_DIV);
        elementsByClass.html();
        System.out.println(elementsByClass);

    }
}
