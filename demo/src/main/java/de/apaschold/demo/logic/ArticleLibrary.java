package de.apaschold.demo.logic;

import de.apaschold.demo.logic.filehandling.TextFileHandler;
import de.apaschold.demo.model.ArticleReference;

import java.util.List;

public class ArticleLibrary {
    //0. constants

    //1. attributes
    private List<ArticleReference> articles;

    //2. constructors
    public ArticleLibrary(String activeLibraryFilePath) {
        fillLibraryFromFile(activeLibraryFilePath);
    }

    //3. getter and setter methods
    public List<ArticleReference> getArticles() {
        return this.articles;
    }

    //4. other methods
    public void clear(){
        this.articles.clear();
    }

    public String generateStringForBibTex (){
        StringBuilder libraryAsBibTex = new StringBuilder();

        for(ArticleReference reference : this.articles) {
            libraryAsBibTex.append(reference.exportAsBibTexString()).append("\n\n");
        }

        return libraryAsBibTex.toString();
    }

    public void addArticle(ArticleReference article){
        this.articles.add(article);
    }

    public void fillLibraryFromFile(String activeLibraryFilePath){
        this.articles = TextFileHandler.getInstance().importLibraryFromCsvFile(activeLibraryFilePath);
    }
}
