package de.apaschold.demo.gui.mainview;

import com.dansoftware.pdfdisplayer.PDFDisplayer;
import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.Alerts;
import de.apaschold.demo.gui.GuiController;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;

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
    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        this.journalArticle = (JournalArticle) GuiController.getInstance().getSelectedArticle();

        populateJournalArticleView();

        this.displayer = new PDFDisplayer();
        this.pdfViewer.setCenter(displayer.toNode());
    }

    //4. FXML methods
    @FXML
    private void saveChanges() throws IOException {
        if (this.journalArticle != null){
            this.journalArticle.setTitle( this.titleChange.getText());
            this.journalArticle.setAuthor( this.authorsChange.getText().replace("\n", ", "));
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

    @FXML
    private void selectAttachedFile() throws IOException{
        //replace file format by the folder extension
        String folderPath = GuiController.getInstance().getActiveLibraryFilePath()
                .replace(AppTexts.LIBRARY_FILE_FORMAT, AppTexts.FOLDER_EXTENSION + "\\");

        String filePath = folderPath + this.attachedFiles.getValue();

        displayer.loadPDF(new File(filePath));
    }

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
        } else {
            Alerts.showInformationNoFileChoosen();
        }
    }

    //5. other methods
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

    private void populatePDFViewerTab(){
        this.attachedFiles.getItems().setAll(this.journalArticle.getPdfFilePaths());
    }
}
