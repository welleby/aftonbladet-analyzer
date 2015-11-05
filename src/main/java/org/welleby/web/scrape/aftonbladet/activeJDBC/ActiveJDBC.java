package org.welleby.web.scrape.aftonbladet.activeJDBC;

import org.javalite.activejdbc.Base;

import java.util.List;

/**
 * Created by kerling on 05/11/15.
 */
public class ActiveJDBC {

    public static void main(String[] args) {
        Base.open("org.sqlite.JDBC", "jdbc:sqlite:test.db", "", "");
        List<Article> articles = Article.where("title = ?", "Supermåne");
        System.out.println(articles);
    }
}
