package de.apaschold.demo.gui.citationdetailsview;

import com.dansoftware.pdfdisplayer.PDFDisplayer;
import de.apaschold.demo.HelloApplication;
import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.gui.Alerts;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.logic.CitationFactory;
import de.apaschold.demo.logic.filehandling.FileHandler;
import de.apaschold.demo.logic.filehandling.SeleniumWebHandlerHeadless;
import de.apaschold.demo.logic.filehandling.WebHandler;
import de.apaschold.demo.model.Citation;
import de.apaschold.demo.model.CitationType;
import de.apaschold.demo.model.JournalArticle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

public class CitationDetailsViewController implements Initializable {
    //0. constants

    //1. attributes
    private Citation citation;
    private PDFDisplayer displayer;

    //2. FXML elements
    @FXML
    private Label citationDetails;

    @FXML
    private BorderPane editTab;
    @FXML
    private ComboBox<CitationType> editCitationType;

    @FXML
    private ComboBox<String> attachedFiles;
    @FXML
    private BorderPane pdfViewer;

    @FXML
    private Button updateCitationButton;
    @FXML
    private Button searchPDFButton;

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
        this.citation = GuiController.getInstance().getSelectedCitation();

        createDummyCitationToEdit();

        populateCitationDetailsView();

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
        String pubMedString = this.citation.createPubMedSearchTerm();

        //returned String is %journal_title|year|||author_last_name|Art1|pub_med_id
        String pubMedId = WebHandler.getInstance().getPubMedId(pubMedString).split("Art1\\|")[1];

        if(!pubMedId.contains("NOT_FOUND")) {
            JSONObject recordsFromPubMedId = WebHandler.getInstance().getRecordsFromPubMedId(pubMedId);

            GuiController.getInstance().setReferenceChanges(recordsFromPubMedId);
            GuiController.getInstance().loadReferenceUpdateView();

            GuiController.getInstance().loadMainMenu();
        } else {
            Alerts.showInformationRecordNotFound();
        }
    }

    /** <h2>selectAttachedFile</h2>
     * <li>Selects and displays the attached PDF file in the PDF viewer.</li>
     * <li>Linked to the {@link ComboBox} in "Attached Pdfs"-Tab</li>
     * <li>Checks if file name is not empty and if file exists</li>
     *
     * @throws IOException if an I/O error occurs while loading the PDF file.
     */
    @FXML
    private void selectAttachedFile() throws IOException{
        String fileName = this.attachedFiles.getValue();

        if (!fileName.equals(AppTexts.PLACEHOLDER)) {
            //replace file format by the folder extension
            String folderPath = GuiController.getInstance().getActiveLibraryFilePath()
                    .replace(AppTexts.LIBRARY_FILE_FORMAT, AppTexts.PDF_FOLDER_EXTENSION);

            String filePath = folderPath + fileName;
            File selectedFile = new File(filePath);

            if (selectedFile.exists()) {
                displayer.loadPDF(selectedFile);
            } else {
                Alerts.showInformationFileNotFoundInFolder(fileName);
            }
        } else {
            Alerts.showInformationNoFileChosen();
        }
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
            GuiController.getInstance().addNewAttachmentToCitationReference(chosenFile.getName());

            populatePDFViewerTab();

            FileHandler.getInstance().copySelectedAttachmentToPdfFolder(chosenFile);
        } else {
            Alerts.showInformationNoFileChosen();
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
            this.citation.removeAttachment(chosenAttachment);

            populatePDFViewerTab();

            FileHandler.getInstance().deleteSelectedAttachmentFromFolder(chosenAttachment);
        } else {
            Alerts.showInformationNoFileChosen();
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
            SeleniumWebHandlerHeadless.getInstance().downloadPdfFrom(AppTexts.HTTPS_FOR_DOI + this.citation.getDoi());

            String latestAddedFile = FileHandler.getInstance().determineLatestAddedFile();
            GuiController.getInstance().addNewAttachmentToCitationReference(latestAddedFile);

            populatePDFViewerTab();
        } catch (Exception e){
            System.err.println("Pdf not found!");
        }
    }

    /** <h2>changeCitationType</h2>
     * <li>Changes the {@link CitationType} of the dummy {@link Citation} to edit.</li>
     * <li>Recreates the dummy citation with the new citation type and updates the GUI accordingly.</li>
     * <li>Linked to the {@link ComboBox} in "Edit"-Tab</li>
     */
    @FXML
    protected void changeCitationType(){
        Citation dummyCitation = GuiController.getInstance().getDummyCitationToEdit();

        CitationType newType = this.editCitationType.getValue();

        if (dummyCitation.getCitationType() != newType){
            dummyCitation.setCitationType(newType);

            Citation dummyCitationWithNewCitationType = CitationFactory
                    .createCitationFromCsvLine(dummyCitation.toCsvString());
            GuiController.getInstance().setDummyCitationToEdit(dummyCitationWithNewCitationType);

            populateCitationEditTab();
        }
    }

    //5. other methods
    /** <h2>populateCitationDetailsView</h2>
     * <li>Populates the citation details view with the details of the selected {@link Citation}.</li>
     * <li>Calls methods for each Tab</li>
     * <li>Activates/Hide buttons</li>
     */

    public void populateCitationDetailsView(){
        //populate the labels in the article overview
        this.citationDetails.setText(this.citation.citationDetailsAsString());

        //populate the article edit view
        this.editCitationType.getItems().setAll(CitationType.values());
        this.editCitationType.setValue(GuiController.getInstance().getDummyCitationToEdit().getCitationType());
        populateCitationEditTab();


        populatePDFViewerTab();

        //activate/deactivate buttons
        if (this.citation instanceof JournalArticle){
            this.searchPDFButton.setVisible(true);
            this.updateCitationButton.setVisible(true);
        } else {
            this.searchPDFButton.setVisible(false);
            this.updateCitationButton.setVisible(false);
        }
    }

    /** <h2>populateCitationEditTab</h2>
     * <li>Populates the citation edit tab with the appropriate subview based on the citation type.</li>
     */

    private void populateCitationEditTab() {
        String fxmlFile = switch (GuiController.getInstance().getDummyCitationToEdit().getCitationType()) {
                case JOURNAL_ARTICLE -> "journal-article-subview.fxml";
                case BOOK_SECTION -> "book-section-subview.fxml";
                case BOOK -> "book-subview.fxml";
                case THESIS -> "phd-thesis-subview.fxml";
                case PATENT -> "patent-subview.fxml";
                case UNPUBLISHED -> "unpublished-subview.fxml";
                default -> "";
            };

        if (!fxmlFile.isEmpty()) {
            try {
                Parent citationEditView = FXMLLoader.load(HelloApplication.class.getResource(fxmlFile));
                this.editTab.setCenter(citationEditView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** <h2>createDummyCitationToEdit</h2>
     * <li>Creates a dummy {@link Citation} to edit by converting the selected citation to a CSV string
     * and then creating a new citation from that string.</li>
     * <li>Sets the dummy citation as the citation to edit in the {@link GuiController}.</li>
     */
    private void createDummyCitationToEdit() {
        String csvStringOfSelectedCitation = this.citation.toCsvString();

        Citation dummyCitationToEdit = CitationFactory.createCitationFromCsvLine(csvStringOfSelectedCitation);

        GuiController.getInstance().setDummyCitationToEdit(dummyCitationToEdit);
    }

    /** <h2>populatePDFViewerTab</h2>
     * <li>Populates the {@link ComboBox} PDF viewer tab with the attached PDF files of the {@link Citation}.</li>
     */
    private void populatePDFViewerTab(){
        this.attachedFiles.getItems().setAll(this.citation.getPdfFilePaths());
    }
}
