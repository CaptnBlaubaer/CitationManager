package de.apaschold.demo.gui.citationdetailsview;

import com.dansoftware.pdfdisplayer.PDFDisplayer;
import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.Unpublished;
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
 * <h2>UnpublishedSubViewController</h2>
 * <li>Controller for the unpublished article sub view.</li>
 * <li>Handles displaying and editing unpublished article details
 * as well as displaying attached PDF files.</li>
 */

public class UnpublishedSubViewController implements Initializable {
    //0. constants

    //1. attributes
    private Unpublished unpublished;
    private PDFDisplayer displayer;

    //2. FXML elements
    @FXML
    private Label articleType;
    @FXML
    private Label title;
    @FXML
    private Label authors;
    @FXML
    private Label year;

    @FXML
    private TextField titleChange;
    @FXML
    private TextArea authorsChange;
    @FXML
    private TextField yearChange;

    @FXML
    private ComboBox<String> attachedFiles;
    @FXML
    private BorderPane pdfViewer;

    //3. constructors/initialize method
    /** <h2>initialize</h2>
     * <li>Initializes the controller by populating the unpublished article details
     * and setting up the PDF viewer.</li>
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        this.unpublished = (Unpublished) GuiController.getInstance().getSelectedArticle();

        populateUnpublishedView();

        this.displayer = new PDFDisplayer();
        this.pdfViewer.setCenter(displayer.toNode());
    }

    //4. FXML methods
    /** <h2>saveChanges</h2>
     * <li>Saves the changes made to the unpublished article details.</li>
     * <li>Updates the main menu to reflect the changes.</li>
     * @throws IOException if an I/O error occurs while loading the main menu.
     */

    @FXML
    private void saveChanges() throws IOException {
        if (this.unpublished != null){
            this.unpublished.setTitle( this.titleChange.getText());
            this.unpublished.setAuthors( this.authorsChange.getText().replace("\n", ", "));

            this.unpublished.setYear( MyLittleHelpers.convertStringInputToInteger(this.yearChange.getText()));

            //update the labels in the article overview
            GuiController.getInstance().loadMainMenu();

        }
    }

    /** <h2>selectAttachedFile</h2>
     * <li>Loads the selected attached PDF file into the PDF viewer.</li>
     * @throws IOException if an I/O error occurs while loading the PDF file.
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
    /** <h2>populateUnpublishedView</h2>
     * <li>Populates the Unpublished view with the details of the selected journal article.</li>
     * <li>Calls methods for each Tab</li>
     */

    public void populateUnpublishedView(){
        String yearAsString = "-";
        if (this.unpublished.getYear() != 0){
            yearAsString = String.valueOf(this.unpublished.getYear());
        }

        //populate the labels in the article overview
        populateArticleOverviewTab(yearAsString);

        //populate the textfields in the article edit view
        populateArticleEditTab(yearAsString);

        populatePDFViewerTab();
    }

    /** <h2>populateArticleOverviewTab</h2>
     * <li>Populates the article overview tab with the details of the unpublished article.</li>
     * @param yearAsString The year of the unpublished article as a string.
     */

    private void populateArticleOverviewTab(String yearAsString) {
        this.articleType.setText("Type: " + this.unpublished.getArticleType().getDescription());
        this.title.setText("Title: " + this.unpublished.getTitle());
        this.authors.setText("Authors: " + this.unpublished.getAuthor().replace("; ", "\n"));
        this.year.setText("Year: " + yearAsString);
    }

    /** <h2>populateArticleEditTab</h2>
     * <li>Populates the article edit tab with the details of the unpublished article.</li>
     * @param yearAsString The year of the unpublished article as a string.
     */

    private void populateArticleEditTab(String yearAsString) {
        this.titleChange.setText(this.unpublished.getTitle());
        this.authorsChange.setText(this.unpublished.getAuthor().replace("; ", "\n"));
        this.yearChange.setText(yearAsString);
    }

    /** <h2>populatePDFViewerTab</h2>
     * <li>Populates the PDF viewer tab with the attached PDF files of the unpublished article.</li>
     */
    private void populatePDFViewerTab(){
        this.attachedFiles.getItems().setAll(this.unpublished.getPdfFilePaths());
    }
}
