package de.apaschold.demo.gui.mainview;

import com.dansoftware.pdfdisplayer.PDFDisplayer;
import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.Patent;
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

public class PatentSubViewController implements Initializable {
    //0. constants

    //1. attributes
    private Patent patent;
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
    private Label url;

    @FXML
    private TextField titleChange;
    @FXML
    private TextArea authorsChange;
    @FXML
    private TextField yearChange;
    @FXML
    private TextField urlChange;

    @FXML
    private ComboBox<String> attachedFiles;
    @FXML
    private BorderPane pdfViewer;

    //3. constructors/initialize method
    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        this.patent = (Patent) GuiController.getInstance().getSelectedArticle();

        populateBookSubView();

        this.displayer = new PDFDisplayer();
        this.pdfViewer.setCenter(displayer.toNode());
    }

    //4. FXML methods
    @FXML
    private void saveChanges() throws IOException {
        if (this.patent != null){
            this.patent.setTitle( this.titleChange.getText());
            this.patent.setAuthors( this.authorsChange.getText().replace("\n", ", "));

            this.patent.setYear( MyLittleHelpers.convertStringInputToInteger(this.yearChange.getText()));

            this.patent.setDoi(this.urlChange.getText());

            //update the labels in the article overview
            GuiController.getInstance().loadMainMenu();
        }
    }


    @FXML
    private void selectAttachedFile() throws IOException{
        //replace file format by the folder extension
        String folderPath = GuiController.getInstance().getActiveLibraryFilePath()
                .replace(AppTexts.LIBRARY_FILE_FORMAT, AppTexts.FOLDER_EXTENSION);

        String filePath = folderPath + this.attachedFiles.getValue();

        displayer.loadPDF(new File(filePath));
    }

    //5. other methods
    public void populateBookSubView(){
        String yearAsString = "-";
        if (this.patent.getYear() != 0){
            yearAsString = String.valueOf(this.patent.getYear());
        }

        //populate the labels in the article overview
        populateArticleOverviewTab(yearAsString);

        //populate the textfields in the article edit view
        populateArticleEditTab(yearAsString);

        populatePDFViewerTab();
    }

    private void populateArticleOverviewTab(String yearAsString) {
        this.articleType.setText("Type: " + this.patent.getArticleType().getDescription());
        this.title.setText("Title: " + this.patent.getTitle());
        this.authors.setText("Authors: " + this.patent.getAuthor().replace("; ", "\n"));
        this.year.setText("Year: " + yearAsString);
        this.url.setText("URL: " + this.patent.getDoi());
    }

    private void populateArticleEditTab(String yearAsString) {
        this.titleChange.setText(this.patent.getTitle());
        this.authorsChange.setText(this.patent.getAuthor().replace("; ", "\n"));
        this.yearChange.setText(yearAsString);
        this.urlChange.setText(this.patent.getDoi());
    }

    private void populatePDFViewerTab(){
        this.attachedFiles.getItems().setAll(this.patent.getPdfFilePaths());
    }
}