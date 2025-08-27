package de.apaschold.demo.gui.addnewarticleview;

import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.ArticleType;
import de.apaschold.demo.model.JournalArticle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddNewArticleView implements Initializable {
    //0. constants

    //1. attributes

    //2. FXML elements
    @FXML
    private ComboBox <ArticleType> newArticleType;
    @FXML
    private TextField newTitle;
    @FXML
    private TextArea newAuthors;
    @FXML
    private TextField newJournal;
    @FXML
    private TextField newJournalShortForm;
    @FXML
    private TextField newYear;
    @FXML
    private TextField newVolume;
    @FXML
    private TextField newIssue;
    @FXML
    private TextField newPages;
    @FXML
    private TextField newDoi;

    //3. constructors/initialize method
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        this.newArticleType.getItems().addAll(ArticleType.values());
        this.newArticleType.setValue(ArticleType.JOURNAL_ARTICLE);
    }

    //4. FXML methods
    @FXML
    protected void saveNewArticle(){
        int newYearAsInteger = MyLittleHelpers.convertStringInputToInteger(this.newYear.getText());
        int newVolumeAsInteger = MyLittleHelpers.convertStringInputToInteger(this.newVolume.getText());
        int newIssueAsInteger= MyLittleHelpers.convertStringInputToInteger(this.newIssue.getText());

        JournalArticle newArticle = new JournalArticle(
                this.newTitle.getText(),
                this.newAuthors.getText(),
                this.newJournal.getText(),
                this.newJournalShortForm.getText(),
                newYearAsInteger,
                newVolumeAsInteger,
                newIssueAsInteger,
                this.newPages.getText(),
                this.newDoi.getText(),
                "null"
        );

        GuiController.getInstance().getArticleContainer().addArticle(newArticle);

        Stage stage = (Stage) newArticleType.getScene().getWindow();
        stage.close();
    }

}
