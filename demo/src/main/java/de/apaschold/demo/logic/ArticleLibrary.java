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
        importLibraryFromFile(activeLibraryFilePath);
    }

    //3. getter and setter methods
    public List<ArticleReference> getArticles() {
        return this.articles;
    }

    //4. methods to modify list
    public void clear(){ this.articles.clear();}

    public void addArticle(ArticleReference article){
        this.articles.add(article);
    }

    public void deleteArticle(ArticleReference article){ this.articles.remove(article);}

    //5. export/import methods
    public String generateStringForBibTex (){
        StringBuilder libraryAsBibTex = new StringBuilder();

        for(ArticleReference reference : this.articles) {
            libraryAsBibTex.append(reference.exportAsBibTexString()).append("\n\n");
        }

        return libraryAsBibTex.toString();
    }

    public void importLibraryFromFile(String activeLibraryFilePath){
        this.articles = TextFileHandler.getInstance().importLibraryFromCsvFile(activeLibraryFilePath);
    }
}
