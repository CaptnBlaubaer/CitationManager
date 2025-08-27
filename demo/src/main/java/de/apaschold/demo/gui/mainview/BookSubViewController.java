package de.apaschold.demo.gui.mainview;

import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;

public class BookSubViewController implements Initializable {
    //0. constants

    //1. attributes
    private Book book;

    //2. FXML elements
    @FXML
    private Label articleType;
    @FXML
    private Label title;
    @FXML
    private Label authors;
    @FXML
    private Label publisher;
    @FXML
    private Label year;
    @FXML
    private Label volume;
    @FXML
    private Label doi;

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
    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        this.book = (Book) GuiController.getInstance().getSelectedArticle();

        populateBookSubView();
    }

    //4. FXML methods
    @FXML
    private void saveChanges() throws IOException {
        if (this.book != null){
            this.book.setTitle( this.titleChange.getText());
            this.book.setAuthor( this.authorsChange.getText().replace("\n", ", "));
            this.book.setJournal( this.publisherChange.getText());

            this.book.setYear( MyLittleHelpers.convertStringInputToInteger(this.yearChange.getText()));
            this.book.setVolume( MyLittleHelpers.convertStringInputToInteger(this.volumeChange.getText()));

            this.book.setDoi(this.doiChange.getText());

            //update the labels in the article overview
            GuiController.getInstance().loadMainMenu();
        }
    }

    //5. other methods
    public void populateBookSubView(){
        String yearAsString = "-";
        if (this.book.getYear() != 0){
            yearAsString = String.valueOf(this.book.getYear());
        }

        String volumeAsString = "-";
        if (this.book.getVolume() != 0){
            volumeAsString = String.valueOf(this.book.getVolume());
        }

        //populate the labels in the article overview
        populateArticleOverviewTab(yearAsString, volumeAsString);

        //populate the textfields in the article edit view
        populateArticleEditTab(yearAsString, volumeAsString);

    }

    private void populateArticleOverviewTab(String yearAsString, String volumeAsString) {
        this.articleType.setText("Type: " + this.book.getArticleType().getDescription());
        this.title.setText("Title: " + this.book.getTitle());
        this.authors.setText("Authors: " + this.book.getAuthor().replace("; ", "\n"));
        this.publisher.setText("Publisher: " + this.book.getJournal());
        this.year.setText("Year: " + yearAsString);
        this.volume.setText("Volume: " + volumeAsString);
        this.doi.setText("DOI: " + this.book.getDoi());
    }

    private void populateArticleEditTab(String yearAsString,String volumeAsString) {
        this.titleChange.setText(this.book.getTitle());
        this.authorsChange.setText(this.book.getAuthor().replace("; ", "\n"));
        this.publisherChange.setText(this.book.getJournal());
        this.yearChange.setText(yearAsString);
        this.volumeChange.setText(volumeAsString);
        this.doiChange.setText(this.book.getDoi());
    }
}
