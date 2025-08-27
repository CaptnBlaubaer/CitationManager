package de.apaschold.demo.gui.mainview;

import de.apaschold.demo.HelloApplication;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.Article;
import de.apaschold.demo.model.JournalArticle;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainViewController implements Initializable {
    //0. constants
    private static final String GENERAL_FXML_PATH = "C:/Users/Hein/Desktop/Programmierstuff/JAva/CitationManager/demo/target/classes//de/apaschold/demo/";

    //1. attributes

    //2. FXML elements
    @FXML
    private TableView<Article> articlesTable;
    @FXML
    private TableColumn<Article, String> titleColumn;
    @FXML
    private TableColumn<Article, String> authorsColumn;
    @FXML
    private TableColumn<Article, String> journalColumn;
    @FXML
    private TableColumn<Article, Integer> yearColumn;

    @FXML
    private BorderPane articleView;
    @FXML
    private JournalArticleViewController journalArticleViewController;


    //3. constructors/initialize method
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        populateTable();

        if(GuiController.getInstance().getSelectedArticle() != null){
            populateArticleView();
        }
    }

    //4. FXML methods
    @FXML
    protected void saveArticlesToCsv() {
        GuiController.getInstance().getArticleContainer().saveArticlesToCsv();
    }

    @FXML
    protected void addNewArticle() {
        GuiController.getInstance().loadAddNewArticleView();

        // Refresh the table to show the newly added article
        populateTable();
    }

    @FXML
    protected void updateLibrary(){
        populateTable();
    }

    //5. other methods
    protected void populateTable(){
        List<Article> articles = GuiController.getInstance().getArticleList();

        if (!articles.isEmpty()){
            this.articlesTable.getItems().clear();
            this.articlesTable.getItems().addAll(articles);

            this.titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
            this.authorsColumn.setCellValueFactory(cellData -> cellData.getValue().authorsProperty());
            this.journalColumn.setCellValueFactory(cellData -> cellData.getValue().journalProperty());
            this.yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        } else {
            System.out.println("No articles found to display in the table.");
        }

        this.articlesTable.getSelectionModel().selectedItemProperty().addListener(getSelectionListener());
    }

    private ChangeListener<Article> getSelectionListener(){
        return (observable, oldArticle, newArticle) -> {
            GuiController.getInstance().setSelectedArticle(newArticle);

            populateArticleView();
        };
    }

    public void populateArticleView(){
        Article selectedArticle = GuiController.getInstance().getSelectedArticle();

        switch (selectedArticle.getArticleType()){
            case JOURNAL_ARTICLE -> showJournalArticleView();
            //case "Book" -> showBookView();
            //case "Conference Paper" -> showConferencePaperView();
            default -> System.out.println("Unknown article type: " + selectedArticle.getArticleType());
        }
    }

    public void showJournalArticleView() {
        try {
            Parent journalArticleView = FXMLLoader.load(HelloApplication.class.getResource("journal-article-view.fxml"));
            this.articleView.setCenter(journalArticleView);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}