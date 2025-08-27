package de.apaschold.demo.gui.mainview;

import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.Article;
import de.apaschold.demo.model.JournalArticle;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.util.List;

public class MainViewController implements Initializable {
    //0. constants
    private static final String GENERAL_FXML_PATH = "file://C:/Users/Hein/Desktop/Programmierstuff/JAva/CitationManager/demo/src/main/resources/de/apaschold/demo/";

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
    private Parent journalArticleView;
    @FXML
    private JournalArticleViewController journalArticleViewController;


    //3. constructors/initialize method
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        populateTable();
    }

    //4. FXML methods
    @FXML
    protected void saveArticlesToCsv() {
        GuiController.getInstance().getArticleContainer().saveArticlesToCsv();
    }

    @FXML
    protected void addNewArticle() {
        GuiController.getInstance().loadAddNewArticleView();
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
            switch (newArticle.getArticleType()){
                case JOURNAL_ARTICLE -> journalArticleViewController.selectJournalArticle((JournalArticle) newArticle);
                case BOOK -> System.out.println("Book selected: " + newArticle.getTitle());
                case BOOK_CHAPTER -> System.out.println("Book Chapter selected: " + newArticle.getTitle());
                default -> System.out.println("Unknown article type selected.");
            }
        };
    }


}