package de.apaschold.demo.gui.mainview;

import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.BookSection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;

public class BookSectionSubViewController implements Initializable {
    //0. constants

    //1. attributes
    private BookSection bookSection;

    //2. FXML elements
    @FXML
    private Label articleType;
    @FXML
    private Label title;
    @FXML
    private Label authors;
    @FXML
    private Label booktitle;
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
    private TextField booktitleChange;
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
    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        this.bookSection = (BookSection) GuiController.getInstance().getSelectedArticle();

        populateJournalArticleView();
    }

    //4. FXML methods
    @FXML
    private void saveChanges() throws IOException {
        if (this.bookSection != null){
            this.bookSection.setTitle( this.titleChange.getText());
            this.bookSection.setAuthor( this.authorsChange.getText().replace("\n", ", "));
            this.bookSection.setTitle( this.booktitleChange.getText());
            this.bookSection.setAuthor( this.editorsChange.getText().replace("\n", ", "));
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

    }

    private void populateArticleOverviewTab(String yearAsString, String volumeAsString) {
        this.articleType.setText("Type: " + this.bookSection.getArticleType().getDescription());
        this.title.setText("Title: " + this.bookSection.getTitle());
        this.authors.setText("Authors: " + this.bookSection.getAuthor().replace("; ", "\n"));
        this.booktitle.setText("Title: " + this.bookSection.getBooktitle());
        this.editors.setText("Authors: " + this.bookSection.getEditor().replace("; ", "\n"));
        this.publisher.setText("Journal: " + this.bookSection.getJournal());
        this.year.setText("Year: " + yearAsString);
        this.volume.setText("Volume: " + volumeAsString);
        this.pages.setText("Pages: " + this.bookSection.getPages());
        this.doi.setText("DOI: " + this.bookSection.getDoi());
    }

    private void populateArticleEditTab(String yearAsString,String volumeAsString) {
        this.titleChange.setText(this.bookSection.getTitle());
        this.authorsChange.setText(this.bookSection.getAuthor().replace("; ", "\n"));
        this.booktitleChange.setText("Title: " + this.bookSection.getBooktitle());
        this.editorsChange.setText("Authors: " + this.bookSection.getEditor().replace("; ", "\n"));
        this.publisherChange.setText(this.bookSection.getJournal());
        this.yearChange.setText(yearAsString);
        this.volumeChange.setText(volumeAsString);
        this.pagesChange.setText(this.bookSection.getPages());
        this.doiChange.setText(this.bookSection.getDoi());
    }
}
