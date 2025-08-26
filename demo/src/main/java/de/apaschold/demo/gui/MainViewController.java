package de.apaschold.demo.gui;

import de.apaschold.demo.model.Article;
import de.apaschold.demo.model.JournalArticle;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class MainViewController implements Initializable {
    //0. constants

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
    private Label journalArticleTitle;
    @FXML
    private Label journalArticleAuthors;
    @FXML
    private Label journalArticleJournal;
    @FXML
    private Label journalArticleJournalShortForm;
    @FXML
    private Label journalArticleYear;
    @FXML
    private Label journalArticleVolume;
    @FXML
    private Label journalArticleIssue;
    @FXML
    private Label journalArticlePages;
    @FXML
    private Label journalArticleDoi;

    //3. constructors/initialize method
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        populateTable();
    }

    //4. FXML methods

    //5. other methods
    private void populateTable(){
        List<Article> articles = GuiController.getInstance().getArticles();

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
                case JOURNAL_ARTICLE -> selectJournalArticle((JournalArticle) newArticle);
                case BOOK -> System.out.println("Book selected: " + newArticle.getTitle());
                case BOOK_CHAPTER -> System.out.println("Book Chapter selected: " + newArticle.getTitle());
                default -> System.out.println("Unknown article type selected.");
            }
        };
    }

    private void selectJournalArticle(JournalArticle journalArticle){
        this.journalArticleTitle.setText(journalArticle.getTitle());
        this.journalArticleAuthors.setText(journalArticle.getAuthor().replace(", ", "\n"));
        this.journalArticleJournal.setText(journalArticle.getJournal());
        this.journalArticleJournalShortForm.setText(journalArticle.getJournalShortForm());
        this.journalArticleYear.setText(String.valueOf(journalArticle.getYear()));
        this.journalArticleVolume.setText(journalArticle.getVolume()+"");
        this.journalArticleIssue.setText(journalArticle.getIssue()+"");
        this.journalArticlePages.setText(journalArticle.getPages());
        this.journalArticleDoi.setText(journalArticle.getDoi());
    }
}