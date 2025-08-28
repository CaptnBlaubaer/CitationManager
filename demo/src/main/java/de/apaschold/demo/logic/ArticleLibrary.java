package de.apaschold.demo.logic;

import de.apaschold.demo.logic.filehandling.TextFileHandler;
import de.apaschold.demo.model.ArticleReference;

import java.util.List;

public class ArticleLibrary {
    //0. constants

    //1. attributes
    private final List<ArticleReference> articles;
    private String activeLibraryFilePath;

    //2. constructors
    public ArticleLibrary() {
        this.activeLibraryFilePath = TextFileHandler.getInstance().loadLibraryFilePath();

        this.articles = TextFileHandler.getInstance().importLibraryFromCsvFile(this.activeLibraryFilePath);
    }

    //3. getter and setter methods
    public List<ArticleReference> getArticles() {
        return this.articles;
    }

    public void setActiveLibraryFilePath(String activeLibraryFilePath) {
        this.activeLibraryFilePath = activeLibraryFilePath;
    }

    //4. other methods
    public void saveToCsv(){
        TextFileHandler.getInstance().exportLibraryToCsv(this.articles, activeLibraryFilePath);
    }

    public void exportToBibTex(){
        StringBuilder libraryAsBibTex = new StringBuilder();
        for(ArticleReference reference : this.articles){
            libraryAsBibTex.append(reference.exportAsBibTexString()).append("\n");
        }
    }

    public void addArticle(ArticleReference article){
        this.articles.add(article);
    }
}
