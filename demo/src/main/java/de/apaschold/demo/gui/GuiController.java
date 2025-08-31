package de.apaschold.demo.gui;

import de.apaschold.demo.HelloApplication;
import de.apaschold.demo.logic.ArticleLibrary;
import de.apaschold.demo.logic.filehandling.TextFileHandler;
import de.apaschold.demo.model.ArticleReference;
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
    private final ArticleLibrary library;
    private ArticleReference selectedArticle;
    private String activeLibraryFilePath;

    //2. constructors
    private GuiController() {
        this.activeLibraryFilePath = TextFileHandler.getInstance().loadLibraryFilePath();

        this.library = new ArticleLibrary( this.activeLibraryFilePath);
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

    public List<ArticleReference> getArticleList() {
        return this.library.getArticles();
    }

    public ArticleLibrary getArticleLibrary() {
        return this.library;
    }

    public ArticleReference getSelectedArticle() { return this.selectedArticle;}

    public void setSelectedArticle(ArticleReference selectedArticle) { this.selectedArticle = selectedArticle;}

    public String getActiveLibraryFilePath() { return this.activeLibraryFilePath;}

    public void setActiveLibraryFilePath(String activeLibraryFilePath) { this.activeLibraryFilePath = activeLibraryFilePath;}

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

    public void loadCreateNewLibraryView() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-new-library-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 300, 130);
            Stage newArticleStage = new Stage();
            newArticleStage.setTitle("Create empty library");
            newArticleStage.setScene(scene);
            newArticleStage.showAndWait();

            this.library.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //5. other
    public void exportActiveLibraryToBibTex(){
        String bibTexString = this.library.generateStringForBibTex();

        if(!bibTexString.isEmpty()) {
            String bibTexFilePath = this.activeLibraryFilePath.replace(".cml",".bib");

            TextFileHandler.getInstance().exportLibraryToBibTex(bibTexString, bibTexFilePath);
        } else {
            throw new NullPointerException();
        }
    }

    public void saveActiveLibraryToCsv(){
        TextFileHandler.getInstance().exportLibraryToCsv(this.library.getArticles(), this.activeLibraryFilePath);
    }

    public void fillLibraryFromChosenFile(String activeLibraryFilePath){
        this.library.fillLibraryFromFile(activeLibraryFilePath);
    }
}
