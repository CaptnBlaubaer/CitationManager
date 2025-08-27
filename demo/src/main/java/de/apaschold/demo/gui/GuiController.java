package de.apaschold.demo.gui;

import de.apaschold.demo.HelloApplication;
import de.apaschold.demo.logic.ArticleContainer;
import de.apaschold.demo.model.Article;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GuiController {
    //0. constants

    //1. attributes
    private static GuiController instance;
    private Stage mainStage;
    private final ArticleContainer articles;
    private Article selectedArticle;

    //2. constructors
    private GuiController() {
        this.articles = new ArticleContainer();
    }

    public static synchronized GuiController getInstance() {
        if (instance == null) {
            instance = new GuiController();
        }
        return instance;
    }

    //3. getter and setter methods
    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public List<Article> getArticleList() {
        return articles.getArticles();
    }

    public ArticleContainer getArticleContainer() {
        return articles;
    }

    public Article getSelectedArticle() { return selectedArticle;}

    public void setSelectedArticle(Article selectedArticle) { this.selectedArticle = selectedArticle;}

    //4. open view methods
    public void loadMainMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1680, 960);
        this.mainStage.setTitle("Citation Manager");
        this.mainStage.setScene(scene);
        this.mainStage.show();
    }

    public void loadAddNewArticleView() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-new-article-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 320, 500);
            Stage newArticleStage = new Stage();
            newArticleStage.setTitle("Add New Article");
            newArticleStage.setScene(scene);
            newArticleStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadImportFromBibTexView() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("import-from-bibtex-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 320, 250);
            Stage newArticleStage = new Stage();
            newArticleStage.setTitle("Import from BibTex");
            newArticleStage.setScene(scene);
            newArticleStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
