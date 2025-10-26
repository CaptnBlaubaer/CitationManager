package de.apaschold.demo.gui.citationdetailsview;

import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.JournalArticle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;

/**
 * <h2>JournalArticleSubViewController</h2>
 * <li>Controller for the journal article sub view.</li>
 * <li>Handles displaying and editing journal article details,
 * as well as managing PDF attachments.</li>
 */

public class JournalArticleSubViewController implements Initializable {
    //0. constants

    //1. attributes
    private JournalArticle journalArticle;

    //2. FXML elements
    @FXML
    private TextField titleChange;
    @FXML
    private TextArea authorsChange;
    @FXML
    private TextField journalChange;
    @FXML
    private TextField journalShortFormChange;
    @FXML
    private TextField yearChange;
    @FXML
    private TextField volumeChange;
    @FXML
    private TextField issueChange;
    @FXML
    private TextField pagesChange;
    @FXML
    private TextField doiChange;


    //3. constructors/initialize method
    /**<h2>initialize</h2>
     * <li>Initializes the controller by populating the journal article view
     * and setting up the PDF displayer.</li>
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        this.journalArticle = (JournalArticle) GuiController.getInstance().getSelectedCitation();

        populateEditView();
    }

    //4. FXML methods
    @FXML
    /** <h2>saveChanges</h2>
     * <li>Saves the changes made to the journal article reference manually.</li>
     * <li>Updates the data in the MainView </li>
     * <li>Linked to the Button in "Edit"-Tab</li>
     *
     * @throws IOException if an I/O error occurs during the save process.
     */

    private void saveChanges() throws IOException {
        if (this.journalArticle != null){
            this.journalArticle.setTitle( this.titleChange.getText());
            this.journalArticle.setAuthors( this.authorsChange.getText().replace("\n", "; "));
            this.journalArticle.setJournal( this.journalChange.getText());
            this.journalArticle.setJournalShortForm( this.journalShortFormChange.getText());

            this.journalArticle.setYear( MyLittleHelpers.convertStringInputToInteger(this.yearChange.getText()));
            this.journalArticle.setVolume( MyLittleHelpers.convertStringInputToInteger(this.volumeChange.getText()));
            this.journalArticle.setIssue( MyLittleHelpers.convertStringInputToInteger(this.issueChange.getText()));

            this.journalArticle.setPages(this.pagesChange.getText());
            this.journalArticle.setDoi(this.doiChange.getText());

            //update the labels in the article overview
            GuiController.getInstance().loadMainMenu();
        }
    }

    //5. other methods
    /** <h2>populateJournalArticleView</h2>
     * <li>Populates the journal article  edit view with the details of the selected {@link JournalArticle}.</li>
     * <li>Calls methods for each Tab</li>
     */

    public void populateEditView(){
        String yearAsString = "-";
        if (this.journalArticle.getYear() != 0){
            yearAsString = String.valueOf(this.journalArticle.getYear());
        }

        String volumeAsString = "-";
        if (this.journalArticle.getVolume() != 0){
            volumeAsString = String.valueOf(this.journalArticle.getVolume());
        }

        String issueAsString = "-";
        if (this.journalArticle.getIssue() != 0){
            issueAsString = String.valueOf(this.journalArticle.getIssue());
        }

        this.titleChange.setText(this.journalArticle.getTitle());
        this.authorsChange.setText(this.journalArticle.getAuthor().replace("; ", "\n"));
        this.journalChange.setText(this.journalArticle.getJournal());
        this.journalShortFormChange.setText(this.journalArticle.getJournalShortForm());
        this.yearChange.setText(yearAsString);
        this.volumeChange.setText(volumeAsString);
        this.issueChange.setText(issueAsString);
        this.pagesChange.setText(this.journalArticle.getPages());
        this.doiChange.setText(this.journalArticle.getDoi());
    }
}
