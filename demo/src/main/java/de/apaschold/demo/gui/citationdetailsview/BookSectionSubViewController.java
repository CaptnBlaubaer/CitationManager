package de.apaschold.demo.gui.citationdetailsview;

import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.BookSection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;

/**
 * <h2>BookSectionSubViewController</h2>
 * <li>Controller for the book section sub view.</li>
 * <li>Handles displaying and editing book section details and viewing attached PDF files.</li>
 */
public class BookSectionSubViewController implements Initializable {
    //0. constants

    //1. attributes
    private BookSection bookSection;

    //2. FXML elements
    @FXML
    private TextField titleChange;
    @FXML
    private TextArea authorsChange;
    @FXML
    private TextField bookTitleChange;
    @FXML
    private TextArea editorsChange;
    @FXML
    private TextField publisherChange;
    @FXML
    private TextField yearChange;
    @FXML
    private TextField volumeChange;
    @FXML
    private TextField pagesChange;
    @FXML
    private TextField doiChange;

    //3. constructors/initialize method
    /** <h2>initialize</h2>
     * Initializes the controller, populating the view with the selected book section's details.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        this.bookSection = (BookSection) GuiController.getInstance().getSelectedCitation();

        populateBookSectionView();
    }

    //4. FXML methods
    /** <h2>saveChanges</h2>
     * <li>Saves changes made to the book section details back to the model</li>
     * <li>Updates the main menu view.</li>
     *
     * @throws IOException if an I/O error occurs during saving.
     */
    @FXML
    private void saveChanges() throws IOException {
        if (this.bookSection != null){
            this.bookSection.setTitle( this.titleChange.getText());
            this.bookSection.setAuthors( this.authorsChange.getText().replace("\n", "; "));
            this.bookSection.setBookTitle( this.bookTitleChange.getText());
            this.bookSection.setEditor( this.editorsChange.getText().replace("\n", "; "));
            this.bookSection.setJournal( this.publisherChange.getText());

            this.bookSection.setYear( MyLittleHelpers.convertStringInputToInteger(this.yearChange.getText()));
            this.bookSection.setVolume( MyLittleHelpers.convertStringInputToInteger(this.volumeChange.getText()));

            this.bookSection.setPages(this.pagesChange.getText());
            this.bookSection.setDoi(this.doiChange.getText());

            //update the labels in the article overview
            GuiController.getInstance().loadMainMenu();
        }
    }

    //5. other methods
    /** <h2>populateBookSectionView</h2>
     * <li>Populates the edit view with the details of the selected {@link BookSection}.</li>
     */
    public void populateBookSectionView(){
        String yearAsString = "-";
        if (this.bookSection.getYear() != 0){
            yearAsString = String.valueOf(this.bookSection.getYear());
        }

        String volumeAsString = "-";
        if (this.bookSection.getVolume() != 0){
            volumeAsString = String.valueOf(this.bookSection.getVolume());
        }

        this.titleChange.setText(this.bookSection.getTitle());
        this.authorsChange.setText(this.bookSection.getAuthor().replace("; ", "\n"));
        this.bookTitleChange.setText(this.bookSection.getBookTitle());
        this.editorsChange.setText(this.bookSection.getEditor().replace("; ", "\n"));
        this.publisherChange.setText(this.bookSection.getJournal());
        this.yearChange.setText(yearAsString);
        this.volumeChange.setText(volumeAsString);
        this.pagesChange.setText(this.bookSection.getPages());
        this.doiChange.setText(this.bookSection.getDoi());
    }
}
