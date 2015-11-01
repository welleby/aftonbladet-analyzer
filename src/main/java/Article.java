import org.jsoup.nodes.Document;

import java.sql.Date;

/**
 * Created by kerling on 01/11/15.
 */
public class Article {

    private static final String ARTICLE_DIV = "abBodyText clearfix abJsBodyText";
    private static final String AUTHOR_DIV = "abIconMail abAuthorMail abPfxPrimary";
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
        body = doc.body().getElementsByAttributeValueContaining("class", ARTICLE_DIV).text();
//        This doesn' work since it's a span class and not a div class, look at http://stackoverflow.com/questions/9728854/jsoup-to-get-text-from-span-class for a solution
//        author = doc.body().getElementsByAttributeValueContaining("class", AUTHOR_DIV).text();
        title = doc.title().substring(0,doc.title().indexOf('|')).trim();
        wordCount = body.trim().split("\\s+").length;
        System.out.println(title);
        System.out.println(body);
//        System.out.println(author);
        System.out.println(wordCount);

    }
}
