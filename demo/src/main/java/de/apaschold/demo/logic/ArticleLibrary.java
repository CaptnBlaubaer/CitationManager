package de.apaschold.demo.logic;

import de.apaschold.demo.logic.filehandling.TextFileHandler;
import de.apaschold.demo.model.ArticleReference;

import java.util.List;

public class ArticleLibrary {
    //0. constants

    //1. attributes
    private List<ArticleReference> articles;

    //2. constructors
    /**
     * <h2>ArticleLibrary</h2>
     * <li>Initializes the article library by calling import method.</li>
     *
     * @param activeLibraryFilePath path of the active library file as String
     */
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
    /**
     * <h2>generateStringForBibTex</h2>
     * <li>Generates a BibTex representation of the article library as a String.</li>
     *
     * @return the article library as BibTex String
     */
    public String generateStringForBibTex (){
        StringBuilder libraryAsBibTex = new StringBuilder();

        for(ArticleReference reference : this.articles) {
            libraryAsBibTex.append(reference.exportAsBibTexString()).append("\n\n");
        }

        return libraryAsBibTex.toString();
    }

    /**
     * <h2>importLibraryFromFile</h2>
     * <li>Imports the article library from a CSV file.</li>
     *
     * @param activeLibraryFilePath path of the active library file as String
     */
    public void importLibraryFromFile(String activeLibraryFilePath){
        this.articles = TextFileHandler.getInstance().importLibraryFromCmlFile(activeLibraryFilePath);
    }
}
