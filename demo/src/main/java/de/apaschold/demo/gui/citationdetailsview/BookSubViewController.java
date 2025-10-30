package de.apaschold.demo.gui.citationdetailsview;

import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.Book;
import de.apaschold.demo.model.Citation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;

/**
 * <h2>BookSubViewController</h2>
 * <li>Controller for the book sub view in the article detail view.</li>
 * <li>Handles displaying and editing book-specific information and PDF viewing.</li>
 */

public class BookSubViewController implements Initializable {
    //0. constants

    //1. attributes
    private Book book;

    @FXML
    private TextField titleChange;
    @FXML
    private TextArea authorsChange;
    @FXML
    private TextField publisherChange;
    @FXML
    private TextField yearChange;
    @FXML
    private TextField volumeChange;
    @FXML
    private TextField doiChange;

    //3. constructors/initialize method
    /** <h2>initialize</h2>
     * <li>Initializes the controller by retrieving the selected book from the GuiController.</li>
     * <li>Populates the book sub view with the book's data.</li>
     * <li>Sets up the PDF viewer for displaying attached PDF files.</li>
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        this.book = (Book) GuiController.getInstance().getDummyCitationToEdit();

        populateBookSubView();
    }

    //4. FXML methods
    /** <h2>saveChanges</h2>
     * <li>Saves changes made to the book details back to the dummy {@link Citation}</li>
     * <li>Sets the updated citation as the selected citation in the {@link GuiController}.</li>
     * <li>Updates the main menu view.</li>
     * @throws IOException if an I/O error occurs during the update process.
     */

    @FXML
    private void saveChanges() throws IOException {
        if (this.book != null){
            this.book.setTitle( this.titleChange.getText());
            this.book.setAuthors( this.authorsChange.getText().replace("\n", "; "));
            this.book.setJournal( this.publisherChange.getText());

            this.book.setYear( MyLittleHelpers.convertStringInputToInteger(this.yearChange.getText()));
            this.book.setVolume( MyLittleHelpers.convertStringInputToInteger(this.volumeChange.getText()));

            this.book.setDoi(this.doiChange.getText());

            GuiController.getInstance().updateLibraryWithEditedCitation(this.book);

            //update the labels in the article overview
            GuiController.getInstance().loadMainMenu();
        }
    }

    //5. other methods
    /** <h2>populateBookSubView</h2>
     * <li>Populates the book edit view with the details of the selected {@link Book}.</li>
     * <li>Calls methods for each Tab</li>
     */
    public void populateBookSubView(){
        String yearAsString = "-";
        if (this.book.getYear() != 0){
            yearAsString = String.valueOf(this.book.getYear());
        }

        String volumeAsString = "-";
        if (this.book.getVolume() != 0){
            volumeAsString = String.valueOf(this.book.getVolume());
        }

        this.titleChange.setText(this.book.getTitle());
        this.authorsChange.setText(this.book.getAuthor().replace("; ", "\n"));
        this.publisherChange.setText(this.book.getJournal());
        this.yearChange.setText(yearAsString);
        this.volumeChange.setText(volumeAsString);
        this.doiChange.setText(this.book.getDoi());
    }
}


