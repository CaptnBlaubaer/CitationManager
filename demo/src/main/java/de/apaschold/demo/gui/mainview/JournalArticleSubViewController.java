package de.apaschold.demo.gui.mainview;

import com.dansoftware.pdfdisplayer.PDFDisplayer;
import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.Alerts;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.logic.filehandling.FileHandler;
import de.apaschold.demo.logic.filehandling.SeleniumWebHandlerHeadless;
import de.apaschold.demo.logic.filehandling.WebHandler;
import de.apaschold.demo.model.JournalArticle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.File;
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
    private PDFDisplayer displayer;

    //2. FXML elements
    @FXML
    private Label articleType;
    @FXML
    private Label title;
    @FXML
    private Label authors;
    @FXML
    private Label journal;
    @FXML
    private Label journalShortForm;
    @FXML
    private Label year;
    @FXML
    private Label volume;
    @FXML
    private Label issue;
    @FXML
    private Label  pages;
    @FXML
    private Label doi;

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

    @FXML
    private ComboBox<String> attachedFiles;
    @FXML
    private BorderPane pdfViewer;

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
        this.journalArticle = (JournalArticle) GuiController.getInstance().getSelectedArticle();

        populateJournalArticleView();

        this.displayer = new PDFDisplayer();
        this.pdfViewer.setCenter(displayer.toNode());
    }

    //4. FXML methods
    /** <h2>checkForUpdates</h2>
     * <li>Checks for updates to the journal article reference using PubMed.</li>
     * <li>If updates are found, it loads the reference update view in the GUI controller.</li>
     * <li>Linked to the Button in "Article Info"-Tab</li>
     *
     * @throws IOException if an I/O error occurs during the update check process.
     */

    @FXML
    private void checkForUpdates() throws IOException {
        String pubMedString = this.journalArticle.createPubMedSearchTerm();

        //returned String is %journal_title|year|||author_last_name|Art1|pub_med_id
        String pubMedId = WebHandler.getInstance().getPubMedId(pubMedString).split("Art1\\|")[1];

        if(!pubMedId.equals("NOT_FOUND")) {
            JSONObject recordsFromPubMedId = WebHandler.getInstance().getRecordsFromPubMedId(pubMedId);
            GuiController.getInstance().setReferenceChanges(recordsFromPubMedId);
            GuiController.getInstance().loadReferenceUpdateView();

            GuiController.getInstance().loadMainMenu();
        } else {
            Alerts.showInformationRecordNotFound();
        }
    }

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
            this.journalArticle.setAuthors( this.authorsChange.getText().replace("\n", ", "));
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

    /** <h2>selectAttachedFile</h2>
     * <li>Selects and displays the attached PDF file in the PDF viewer.</li>
     * <li>Linked to the ComboBox in "Attached Pdfs"-Tab</li>
     *
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

    /** <h2>addNewAttachmentToArticleReference</h2>
     * <li>Adds a new attachment to the journal article reference.</li>
     * <li>Opens a file chooser dialog for the user to select a PDF file.</li>
     * <li>Copies the selected file to the PDF folder of the active library and to the article reference.</li>
     * <li>Linked to Button in "Attached Pdfs"-Tab</li>
     */
    @FXML
    private void addNewAttachmentToArticleReference(){
        Stage stage = (Stage) this.pdfViewer.getScene().getWindow();

        String folderPath = GuiController.getInstance().getActiveLibraryFilePath().replaceAll(AppTexts.REGEX_REPLACE_CML_FILENAME,"");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(folderPath));
        fileChooser.setTitle("Choose attachement");
        File chosenFile = fileChooser.showOpenDialog(stage);

        if (chosenFile != null) {
            GuiController.getInstance().addNewAttachmentToArticleReference(chosenFile.getName());

            populatePDFViewerTab();

            FileHandler.getInstance().copySelectedAttachmentToPdfFolder(chosenFile);
        } else {
            Alerts.showInformationNoFileChoosen();
        }
    }

    /** <h2>deleteAttachment</h2>
     * <li>Deletes the selected attachment from the journal article reference and the PDF folder.</li>
     * <li>Linked to Button in "Attached Pdfs"-Tab</li>
     */
    @FXML
    protected void deleteAttachment(){
        String chosenAttachment = this.attachedFiles.getValue();

        if (chosenAttachment != null){
            String attachmentNamesAsString = String.join(",", this.journalArticle.getPdfFilePaths());
            attachmentNamesAsString = attachmentNamesAsString.replace(chosenAttachment,"");

            this.journalArticle.setPdfFilePath(attachmentNamesAsString.split(","));

            populatePDFViewerTab();

            FileHandler.getInstance().deleteSelectedAttachmentFromFolder(chosenAttachment);
        } else {
            Alerts.showInformationNoFileChoosen();
        }
    }

    /** <h2>searchPdfFile</h2>
     * <li>Searches for a PDF file of the journal article using its DOI.</li>
     * <li>Uses {@link SeleniumWebHandlerHeadless} to get .pdf file</li>
     * <li>Downloads the PDF file and adds it as an attachment to the article reference.</li>
     * <li>Linked to Button in "Attached Pdfs"-Tab</li>
     */

    @FXML
    protected void searchPdfFile(){
        try {
            SeleniumWebHandlerHeadless.getInstance().downloadPdfFrom(AppTexts.HTTPS_FOR_DOI + this.journalArticle.getDoi());

            String latestAddedFile = FileHandler.getInstance().determineLatestAddedFile();
            GuiController.getInstance().addNewAttachmentToArticleReference(latestAddedFile);

            populatePDFViewerTab();
        } catch (Exception e){
            System.err.println("Pdf not found!");
        }
    }

    //5. other methods
    /** <h2>populateJournalArticleView</h2>
     * <li>Populates the journal article view with the details of the selected journal article.</li>
     * <li>Calls methods for each Tab</li>
     */

    public void populateJournalArticleView(){
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

        //populate the labels in the article overview
        populateArticleOverviewTab(yearAsString, volumeAsString, issueAsString);

        //populate the textfields in the article edit view
        populateArticleEditTab(yearAsString, volumeAsString, issueAsString);

        populatePDFViewerTab();
    }

    /** <h2>populateArticleOverviewTab</h2>
     * <li>Populates the article overview tab with the details of the journal article.</li>
     *
     * @param yearAsString   The year of publication as a string.
     * @param volumeAsString The volume number as a string.
     * @param issueAsString  The issue number as a string.
     */

    private void populateArticleOverviewTab(String yearAsString, String volumeAsString, String issueAsString) {
        this.articleType.setText("Type: " + this.journalArticle.getArticleType().getDescription());
        this.title.setText("Title: " + this.journalArticle.getTitle());
        this.authors.setText("Authors: " + this.journalArticle.getAuthor().replace("; ", "\n"));
        this.journal.setText("Journal: " + this.journalArticle.getJournal());
        this.journalShortForm.setText("Journal abbreviation: " + this.journalArticle.getJournalShortForm());
        this.year.setText("Year: " + yearAsString);
        this.volume.setText("Volume: " + volumeAsString);
        this.issue.setText("Issue: " + issueAsString);
        this.pages.setText("Pages: " + this.journalArticle.getPages());
        this.doi.setText("DOI: " + this.journalArticle.getDoi());
    }

    /** <h2>populateArticleEditTab</h2>
     * <li>Populates the article edit tab with the details of the journal article.</li>
     *
     * @param yearAsString   The year of publication as a string.
     * @param volumeAsString The volume number as a string.
     * @param issueAsString  The issue number as a string.
     */

    private void populateArticleEditTab(String yearAsString,String volumeAsString,String issueAsString) {
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

    /** <h2>populatePDFViewerTab</h2>
     * <li>Populates the PDF viewer tab with the attached PDF files of the journal article.</li>
     */
    private void populatePDFViewerTab(){
        this.attachedFiles.getItems().setAll(this.journalArticle.getPdfFilePaths());
    }
}
