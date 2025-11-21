package de.apaschold.demo.gui;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.logic.CitationFactory;
import de.apaschold.demo.logic.CitationManager;
import de.apaschold.demo.model.AbstractCitation;
import de.apaschold.demo.model.CitationType;
import de.apaschold.demo.model.StringConverterForCitationType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import static de.apaschold.demo.additionals.MyLittleHelpers.*;

/**
 * <h2>AddNewCitationViewController</h2>
 * <li>Controller for the add new citation view.</li>
 * <li>Handles adding a new {@link AbstractCitation} to the {@link CitationManager}</li>
 */

public class AddNewCitationViewController implements Initializable {
    //0. constants

    //1. attributes

    //2. FXML elements
    @FXML
    private ComboBox <CitationType> newCitationType;
    @FXML
    private Button saveButton;
    @FXML
    private VBox citationForm;


    //3. constructors/initialize method
    /** <h2>initialize</h2>
     * <li>Initializes the article type ComboBox with available ArticleType values.</li>
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        this.newCitationType.getItems().addAll(CitationType.values());

        this.newCitationType.setConverter(new StringConverterForCitationType());
    }

    //4. FXML methods
    /** <h2>chooseCitationType</h2>
     * <li>Sets up the citation form based on the selected {@link CitationType}.</li>
     * <li>Activates the save button</li>
     */
    @FXML
    protected void chooseCitationType(){
        CitationType selectedType = this.newCitationType.getSelectionModel().getSelectedItem();

        this.citationForm.getChildren().clear();

        switch (selectedType) {
            case JOURNAL_ARTICLE -> setUpJournalArticleForm();
            case BOOK -> setUpBookForm();
            case BOOK_SECTION -> setUpBookSectionForm();
            case PATENT -> setUpPatentForm();
            case THESIS -> setUpPhdThesisForm();
            case UNPUBLISHED -> setUpUnpublishedForm();
            default -> System.out.println("Data type not known");
        }

        saveButton.setVisible(true);
    }

    private void setUpJournalArticleForm() {
        citationForm.getChildren().addAll(
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

    private void setUpBookForm() {
        citationForm.getChildren().addAll(
                createNewTextField("Article title"),
                createNewTextArea("Author names"),
                createNewTextField("Publisher"),
                createNewTextField("Year"),
                createNewTextField("Volume"),
                createNewTextField("DOI")
        );
    }

    private void setUpBookSectionForm() {
        citationForm.getChildren().addAll(
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

    private void setUpPatentForm() {
        citationForm.getChildren().addAll(
                createNewTextField("Article title"),
                createNewTextArea("Author names"),
                createNewTextField("Year"),
                createNewTextField("URL")
        );
    }

    private void setUpPhdThesisForm() {
        citationForm.getChildren().addAll(
                createNewTextField("Article title"),
                createNewTextArea("Author names"),
                createNewTextField("Year"),
                createNewTextField("DOI")
        );
    }

    private void setUpUnpublishedForm() {
        citationForm.getChildren().addAll(
                createNewTextField("Article title"),
                createNewTextArea("Author names"),
                createNewTextField("Year")
        );
    }

    /** <h2>saveNewArticle</h2>
     * <li>Collects data from the citation form and creates a new {@link AbstractCitation}e.</li>
     * <li>Adds the new {@link AbstractCitation} to the {@link CitationManager} and closes the add new citation view.</li>
     */
    @FXML
    protected void saveNewCitation() {
        StringBuilder citationDetailsFromManualInputAsString = new StringBuilder();
        citationDetailsFromManualInputAsString
                .append(this.newCitationType.getSelectionModel().getSelectedItem().toString())
                .append(";");

        for (Node node: citationForm.getChildren()) {
            if (node instanceof TextField textField) {
                String inputText = textField.getText();
                if (inputText.isEmpty()) {
                    inputText = AppTexts.PLACEHOLDER;
                }
                citationDetailsFromManualInputAsString
                        .append(inputText.replace(";", ","))
                        .append(";");

            } else if (node instanceof TextArea textArea) {
                String inputText = textArea.getText();
                if (inputText.isEmpty()) {
                    inputText = AppTexts.PLACEHOLDER;
                }

                citationDetailsFromManualInputAsString
                        .append(inputText.replace(";", ",").replace("\n", ","))
                        .append(";");
            }
        }

        AbstractCitation newArticle = CitationFactory.createCitationFromManualDataInput(citationDetailsFromManualInputAsString.toString());

        GuiController.getInstance().addCitationToLibrary(newArticle);

        Stage stage = (Stage) newCitationType.getScene().getWindow();
        stage.close();
    }
}
