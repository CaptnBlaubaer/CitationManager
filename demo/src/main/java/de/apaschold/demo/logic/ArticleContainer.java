package de.apaschold.demo.logic;

import de.apaschold.demo.logic.filehandling.CsvHandler;
import de.apaschold.demo.model.Article;

import java.util.List;

public class ArticleContainer {
    //0. constants

    //1. attributes
    private final List<Article> articles;

    //2. constructors
    public ArticleContainer() {
        this.articles = CsvHandler.getInstance().readArticleInfosCsvFile();
    }

    //3. getter and setter methods
    public List<Article> getArticles() {
        return this.articles;
    }

    //4. other methods
    public void saveArticlesToCsv(){
        CsvHandler.getInstance().writeArticlesToCsv(this.articles);
    }

    public void addArticle(Article article){
        this.articles.add(article);
    }
}
