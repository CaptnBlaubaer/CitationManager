package de.apaschold.demo.gui.citationdetailsview;

import com.dansoftware.pdfdisplayer.PDFDisplayer;
import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.File;
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
        this.book = (Book) GuiController.getInstance().getSelectedArticle();

        populateBookSubView();
    }

    //4. FXML methods
    /** <h2>saveChanges</h2>
     * <li>Saves the changes made in the edit view to the book object.</li>
     * <li>Updates the main menu to reflect the changes.</li>
     * @throws IOException if an I/O error occurs during the update process.
     */

    @FXML
    private void saveChanges() throws IOException {
        if (this.book != null){
            this.book.setTitle( this.titleChange.getText());
            this.book.setAuthors( this.authorsChange.getText().replace("\n", ", "));
            this.book.setJournal( this.publisherChange.getText());

            this.book.setYear( MyLittleHelpers.convertStringInputToInteger(this.yearChange.getText()));
            this.book.setVolume( MyLittleHelpers.convertStringInputToInteger(this.volumeChange.getText()));

            this.book.setDoi(this.doiChange.getText());

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


