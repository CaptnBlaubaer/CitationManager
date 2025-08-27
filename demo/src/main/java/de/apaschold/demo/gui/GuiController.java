package de.apaschold.demo.gui;

import de.apaschold.demo.HelloApplication;
import de.apaschold.demo.logic.filehandling.ArticleContainer;
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
    private ArticleContainer articles;

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
            Scene scene = new Scene(fxmlLoader.load(), 300, 400);
            Stage newArticleStage = new Stage();
            newArticleStage.setTitle("Add New Article");
            newArticleStage.setScene(scene);
            newArticleStage.showAndWait();

            // Refresh articles after the add new article window is closed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
