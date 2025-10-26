package de.apaschold.demo.gui;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.logic.ArticleFactory;
import de.apaschold.demo.model.Citation;
import de.apaschold.demo.model.CitationType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import static de.apaschold.demo.additionals.MyLittleHelpers.*;

/**
 * <h2>AddNewArticleViewController</h2>
 * <li>Controller for the add new article view.</li>
 * <li>Handles adding a new article references to the article library.</li>
 */

public class AddNewArticleViewController implements Initializable {
    //0. constants

    //1. attributes

    //2. FXML elements
    @FXML
    private ComboBox <CitationType> newArticleType;
    @FXML
    private Button saveButton;
    @FXML
    private VBox articleForm;


    //3. constructors/initialize method
    /** <h2>initialize</h2>
     * <li>Initializes the article type ComboBox with available ArticleType values.</li>
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        this.newArticleType.getItems().addAll(CitationType.values());
    }

    //4. FXML methods
    /** <h2>chooseArticleType</h2>
     * <li>Sets up the article form based on the selected ArticleType.</li>
     * <li>Activates the save button</li>
     */
    @FXML
    protected void chooseArticleType(){
        CitationType selectedType = this.newArticleType.getSelectionModel().getSelectedItem();

        this.articleForm.getChildren().clear();

        switch (selectedType) {
            case JOURNAL_ARTICLE -> setUpJournalArticleForm();
            case BOOK -> setUpBookArticleForm();
            case BOOK_SECTION -> setUpBookSectionArticleForm();
            case PATENT -> setUpPatentArticleForm();
            case THESIS -> setUpPhdThesisArticleForm();
            case UNPUBLISHED -> setUpUnpublishedArticleForm();
            default -> System.out.println("Data type not known");
        }

        saveButton.setVisible(true);
    }

    private void setUpJournalArticleForm() {
        articleForm.getChildren().addAll(
                createNewTextField("Article title"),
                createNewTextArea("Author names"),
                createNewTextField("Journal"),
                createNewTextField("Journal abbreviation"),
                createNewTextField("Year"),
                createNewTextField("Volume"),
                createNewTextField("Issue"),
                createNewTextField("Pages"),
                createNewTextField("DOI")
        );
    }

    private void setUpBookArticleForm() {
        articleForm.getChildren().addAll(
                createNewTextField("Article title"),
                createNewTextArea("Author names"),
                createNewTextField("Publisher"),
                createNewTextField("Year"),
                createNewTextField("Volume"),
                createNewTextField("DOI")
        );
    }

    private void setUpBookSectionArticleForm() {
        articleForm.getChildren().addAll(
                createNewTextField("Article title"),
                createNewTextArea("Author names"),
                createNewTextField("Book title"),
                createNewTextArea("Editor names"),
                createNewTextField("Publisher"),
                createNewTextField("Year"),
                createNewTextField("Volume"),
                createNewTextField("Pages"),
                createNewTextField("DOI")
        );
    }

    private void setUpPatentArticleForm() {
        articleForm.getChildren().addAll(
                createNewTextField("Article title"),
                createNewTextArea("Author names"),
                createNewTextField("Year"),
                createNewTextField("URL")
        );
    }

    private void setUpPhdThesisArticleForm() {
        articleForm.getChildren().addAll(
                createNewTextField("Article title"),
                createNewTextArea("Author names"),
                createNewTextField("Year"),
                createNewTextField("DOI")
        );
    }

    private void setUpUnpublishedArticleForm() {
        articleForm.getChildren().addAll(
                createNewTextField("Article title"),
                createNewTextArea("Author names"),
                createNewTextField("Year")
        );
    }

    /** <h2>saveNewArticle</h2>
     * <li>Collects data from the article form and creates a new ArticleReference.</li>
     * <li>Adds the new ArticleReference to the article library and closes the add new article view.</li>
     */
    @FXML
    protected void saveNewArticle() {
        StringBuilder csvLine = new StringBuilder();

        csvLine.append(this.newArticleType.getSelectionModel().getSelectedItem().toString()).append(";");

        for (Node node: articleForm.getChildren()) {
            if (node instanceof TextField textField) {
                csvLine.append(textField.getText().replace(";", ",")).append(";");
            } else if (node instanceof TextArea textArea) {
                csvLine.append(textArea.getText().replace(";", ",").replace("\n",",")).append(";");
            }
        }
        csvLine.append(AppTexts.PLACEHOLDER);

        Citation newArticle = ArticleFactory.createArticleReferenceFromCsvLine(csvLine.toString());

        GuiController.getInstance().getArticleLibrary().addArticle(newArticle);

        Stage stage = (Stage) newArticleType.getScene().getWindow();
        stage.close();
    }
}
