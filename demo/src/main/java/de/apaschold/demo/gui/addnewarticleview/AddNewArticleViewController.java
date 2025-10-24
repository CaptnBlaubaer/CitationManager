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

/**
 * <h2>AddNewArticleViewController</h2>
 * <li>Controller for the add new article view.</li>
 * <li>Handles adding a new article references to the article library.</li>
 */

public class AddNewArticleViewController implements Initializable {
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
    /** <h2>initialize</h2>
     * <li>Initializes the article type ComboBox with available ArticleType values.</li>
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        this.newArticleType.getItems().addAll(ArticleType.values());
        this.newArticleType.setValue(ArticleType.JOURNAL_ARTICLE);
    }

    //4. FXML methods
    /** <h2>saveNewArticle</h2>
     * <li>Creates a new ArticleReference from the input fields and adds it to the article library.</li>
     * <li>Closes the add new article window after saving.</li>
     */
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

        GuiController.getInstance().getArticleLibrary().addArticle(newArticle);

        Stage stage = (Stage) newArticleType.getScene().getWindow();
        stage.close();
    }

}
