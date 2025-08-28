package de.apaschold.demo.gui.mainview;

import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.Unpublished;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;

public class UnpublishedSubViewController implements Initializable {
    //0. constants

    //1. attributes
    private Unpublished unpublished;

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

    //3. constructors/initialize method
    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        this.unpublished = (Unpublished) GuiController.getInstance().getSelectedArticle();

        populateBookSubView();
    }

    //4. FXML methods
    @FXML
    private void saveChanges() throws IOException {
        if (this.unpublished != null){
            this.unpublished.setTitle( this.titleChange.getText());
            this.unpublished.setAuthor( this.authorsChange.getText().replace("\n", ", "));

            this.unpublished.setYear( MyLittleHelpers.convertStringInputToInteger(this.yearChange.getText()));

            //update the labels in the article overview
            GuiController.getInstance().loadMainMenu();
        }
    }

    //5. other methods
    public void populateBookSubView(){
        String yearAsString = "-";
        if (this.unpublished.getYear() != 0){
            yearAsString = String.valueOf(this.unpublished.getYear());
        }

        //populate the labels in the article overview
        populateArticleOverviewTab(yearAsString);

        //populate the textfields in the article edit view
        populateArticleEditTab(yearAsString);

    }

    private void populateArticleOverviewTab(String yearAsString) {
        this.articleType.setText("Type: " + this.unpublished.getArticleType().getDescription());
        this.title.setText("Title: " + this.unpublished.getTitle());
        this.authors.setText("Authors: " + this.unpublished.getAuthor().replace("; ", "\n"));
        this.year.setText("Year: " + yearAsString);
    }

    private void populateArticleEditTab(String yearAsString) {
        this.titleChange.setText(this.unpublished.getTitle());
        this.authorsChange.setText(this.unpublished.getAuthor().replace("; ", "\n"));
        this.yearChange.setText(yearAsString);
    }
}
