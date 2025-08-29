package de.apaschold.demo.gui.mainview;

import de.apaschold.demo.HelloApplication;
import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.ArticleReference;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;

public class MainViewController implements Initializable {
    //0. constants

    //1. attributes

    //2. FXML elements
    @FXML
    private TableView<ArticleReference> articlesTable;
    @FXML
    private TableColumn<ArticleReference, String> titleColumn;
    @FXML
    private TableColumn<ArticleReference, String> authorsColumn;
    @FXML
    private TableColumn<ArticleReference, String> journalColumn;
    @FXML
    private TableColumn<ArticleReference, Integer> yearColumn;

    @FXML
    private BorderPane articleView;

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
        GuiController.getInstance().saveActiveLibraryToCsv();
    }

    @FXML
    protected void addNewArticle() {
        GuiController.getInstance().loadAddNewArticleView();

        // Refresh the table to show the newly added article
        populateTable();
    }

    @FXML
    protected void importFromBibTex(){
        GuiController.getInstance().loadImportFromBibTexView();

        // Refresh the table to show the newly imported article
        populateTable();
    }

    @FXML
    protected void exportToBibTex(){
        try {
            GuiController.getInstance().exportActiveLibraryToBibTex();
        } catch (NullPointerException e) {
            showAlertMessageEmptyLibrary();
        }
    }

    //5. other methods
    protected void populateTable(){
        List<ArticleReference> articles = GuiController.getInstance().getArticleList();

        if (!articles.isEmpty()){
            this.articlesTable.getItems().clear();
            this.articlesTable.getItems().addAll(articles);

            this.titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
            this.authorsColumn.setCellValueFactory(cellData -> cellData.getValue().authorsProperty());
            this.journalColumn.setCellValueFactory(cellData -> cellData.getValue().journalProperty());
            this.yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        } else {
            this.articlesTable.setPlaceholder(new Label( AppTexts.TABLE_VIEW_PLACEHOLDER));
        }

        this.articlesTable.getSelectionModel().selectedItemProperty().addListener(getSelectionListener());
    }

    private ChangeListener<ArticleReference> getSelectionListener(){
        return (observable, oldArticle, newArticle) -> {
            GuiController.getInstance().setSelectedArticle(newArticle);

            populateArticleView();
        };
    }

    public void populateArticleView(){
        ArticleReference selectedArticle = GuiController.getInstance().getSelectedArticle();

        String fxmlFile = switch (selectedArticle.getArticleType()){
            case JOURNAL_ARTICLE -> "main-journal-article-subview.fxml";
            case BOOK_SECTION -> "main-book-section-subview.fxml";
            case BOOK -> "main-book-subview.fxml";
            case THESIS -> "main-phd-thesis-subview.fxml";
            case PATENT -> "main-patent-subview.fxml";
            case UNPUBLISHED -> "main-unpublished-subview.fxml";
            default -> "";
        };

        if(!fxmlFile.isEmpty()) {
            try {
                Parent articleDetailsView = FXMLLoader.load(HelloApplication.class.getResource(fxmlFile));
                this.articleView.setCenter(articleDetailsView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlertMessageEmptyLibrary() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(AppTexts.ALERT_EMPTY_LIST_TITLE);
        alert.setHeaderText(AppTexts.ALERT_EMPTY_LIST_HEADER);
        alert.show();
    }

}