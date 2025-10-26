package de.apaschold.demo.gui.citationdetailsview;

import com.dansoftware.pdfdisplayer.PDFDisplayer;
import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.BookSection;
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
 * <h2>BookSectionSubViewController</h2>
 * <li>Controller for the book section sub view.</li>
 * <li>Handles displaying and editing book section details and viewing attached PDF files.</li>
 */
public class BookSectionSubViewController implements Initializable {
    //0. constants

    //1. attributes
    private BookSection bookSection;
    private PDFDisplayer displayer;

    //2. FXML elements
    @FXML
    private Label articleType;
    @FXML
    private Label title;
    @FXML
    private Label authors;
    @FXML
    private Label bookTitle;
    @FXML
    private Label editors;
    @FXML
    private Label publisher;
    @FXML
    private Label year;
    @FXML
    private Label volume;
    @FXML
    private Label  pages;
    @FXML
    private Label doi;

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

    @FXML
    private ComboBox<String> attachedFiles;
    @FXML
    private BorderPane pdfViewer;

    //3. constructors/initialize method
    /** <h2>initialize</h2>
     * Initializes the controller, populating the view with the selected book section's details.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        this.bookSection = (BookSection) GuiController.getInstance().getSelectedArticle();

        populateJournalArticleView();

        this.displayer = new PDFDisplayer();
        this.pdfViewer.setCenter(displayer.toNode());
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
            this.bookSection.setAuthors( this.authorsChange.getText().replace("\n", ", "));
            this.bookSection.setTitle( this.bookTitleChange.getText());
            this.bookSection.setAuthors( this.editorsChange.getText().replace("\n", ", "));
            this.bookSection.setJournal( this.publisherChange.getText());

            this.bookSection.setYear( MyLittleHelpers.convertStringInputToInteger(this.yearChange.getText()));
            this.bookSection.setVolume( MyLittleHelpers.convertStringInputToInteger(this.volumeChange.getText()));

            this.bookSection.setPages(this.pagesChange.getText());
            this.bookSection.setDoi(this.doiChange.getText());

            //update the labels in the article overview
            GuiController.getInstance().loadMainMenu();
        }
    }

    /** <h2>selectAttachedFile</h2>
     * <li>Loads the selected attached PDF file into the PDF viewer.</li>
     *
     * @throws IOException if an I/O error occurs during file loading.
     */
    @FXML
    private void selectAttachedFile() throws IOException{
        //replace file format by the folder extension
        String folderPath = GuiController.getInstance().getActiveLibraryFilePath()
                .replace(AppTexts.LIBRARY_FILE_FORMAT, AppTexts.PDF_FOLDER_EXTENSION);

        String filePath = folderPath + this.attachedFiles.getValue();

        displayer.loadPDF(new File(filePath));
    }

    //5. other methods
    /** <h2>populateJournalArticleView</h2>
     * <li>Populates the book section view with the details of the selected book section.</li>
     * <li>Calls method for each tab</li>
     */
    public void populateJournalArticleView(){
        String yearAsString = "-";
        if (this.bookSection.getYear() != 0){
            yearAsString = String.valueOf(this.bookSection.getYear());
        }

        String volumeAsString = "-";
        if (this.bookSection.getVolume() != 0){
            volumeAsString = String.valueOf(this.bookSection.getVolume());
        }

        //populate the labels in the article overview
        populateArticleOverviewTab(yearAsString, volumeAsString);

        //populate the textfields in the article edit view
        populateArticleEditTab(yearAsString, volumeAsString);

        populatePDFViewerTab();
    }

    /** <h2>populateArticleOverviewTab</h2>
     * <li>Populates the article overview tab with the book section details.</li>
     *
     * @param yearAsString   The year of publication as a string.
     * @param volumeAsString The volume number as a string.
     */
    private void populateArticleOverviewTab(String yearAsString, String volumeAsString) {
        this.articleType.setText("Type: " + this.bookSection.getArticleType().getDescription());
        this.title.setText("Title: " + this.bookSection.getTitle());
        this.authors.setText("Authors: " + this.bookSection.getAuthor().replace("; ", "\n"));
        this.bookTitle.setText("Book title: " + this.bookSection.getBookTitle());
        this.editors.setText("Authors: " + this.bookSection.getEditor().replace("; ", "\n"));
        this.publisher.setText("Publisher: " + this.bookSection.getJournal());
        this.year.setText("Year: " + yearAsString);
        this.volume.setText("Volume: " + volumeAsString);
        this.pages.setText("Pages: " + this.bookSection.getPages());
        this.doi.setText("DOI: " + this.bookSection.getDoi());
    }

    /** <h2>populateArticleEditTab</h2>
     * <li>Populates the article edit tab with the book section details.</li>
     *
     * @param yearAsString   The year of publication as a string.
     * @param volumeAsString The volume number as a string.
     */
    private void populateArticleEditTab(String yearAsString,String volumeAsString) {
        this.titleChange.setText(this.bookSection.getTitle());
        this.authorsChange.setText(this.bookSection.getAuthor().replace("; ", "\n"));
        this.bookTitleChange.setText("Title: " + this.bookSection.getBookTitle());
        this.editorsChange.setText("Authors: " + this.bookSection.getEditor().replace("; ", "\n"));
        this.publisherChange.setText(this.bookSection.getJournal());
        this.yearChange.setText(yearAsString);
        this.volumeChange.setText(volumeAsString);
        this.pagesChange.setText(this.bookSection.getPages());
        this.doiChange.setText(this.bookSection.getDoi());
    }

    /** <h2>populatePDFViewerTab</h2>
     * <li>Populates the PDF viewer tab with the list of attached PDF files.</li>
     */
    private void populatePDFViewerTab(){
        this.attachedFiles.getItems().setAll(this.bookSection.getPdfFilePaths());
    }
}
