package de.apaschold.demo.gui;

import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.model.JournalArticle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class ReferenceUpdateViewController implements Initializable {
    //0. constants

    //1. attributes
    private String[] referenceChanges;
    private JournalArticle journalArticle;

    //2. FXML
    @FXML
    private TextField oldTitle;
    @FXML
    private TextArea oldAuthors;
    @FXML
    private TextField oldJournal;
    @FXML
    private TextField oldJournalShortForm;
    @FXML
    private TextField oldVolume;
    @FXML
    private TextField oldIssue;
    @FXML
    private TextField oldYear;
    @FXML
    private TextField oldPages;
    @FXML
    private TextField oldDoi;

    @FXML
    private TextField newTitle;
    @FXML
    private TextArea newAuthors;
    @FXML
    private TextField newJournal;
    @FXML
    private TextField newJournalShortForm;
    @FXML
    private TextField newVolume;
    @FXML
    private TextField newIssue;
    @FXML
    private TextField newYear;
    @FXML
    private TextField newPages;
    @FXML
    private TextField newDoi;

    @FXML
    private CheckBox checkTitle;
    @FXML
    private CheckBox checkAuthors;
    @FXML
    private CheckBox checkJournal;
    @FXML
    private CheckBox checkJournalShortForm;
    @FXML
    private CheckBox checkVolume;
    @FXML
    private CheckBox checkIssue;
    @FXML
    private CheckBox checkYear;
    @FXML
    private CheckBox checkPages;
    @FXML
    private CheckBox checkDoi;


    //3. constructor/initialize method
    @Override
    public void initialize (URL location, ResourceBundle resourceBundle){
        this.referenceChanges =
                getValuesFromJson(GuiController.getInstance().getReferenceChangesAsJsonObject());

        this.journalArticle = (JournalArticle) GuiController.getInstance().getSelectedArticle();

        populateOldValues();

        populateNewValues();
    }

    //4. FXML methods
    @FXML
    protected void confirmChanges() {
        updateReference();

        Stage stage = (Stage) this.checkAuthors.getScene().getWindow();

        stage.close();
    }

    @FXML
    protected void discardChanges(){
        Stage stage = (Stage) this.checkAuthors.getScene().getWindow();

        stage.close();
    }

    //5. other methods
    private void populateOldValues() {
        this.oldTitle.setText( this.journalArticle.getTitle());
        this.oldAuthors.setText(this.journalArticle.getAuthor().replaceAll("; ","\n"));
        this.oldJournal.setText(this.journalArticle.getJournal());
        this.oldJournalShortForm.setText(this.journalArticle.getJournalShortForm());
        this.oldVolume.setText(this.journalArticle.getVolume() + "");
        this.oldIssue.setText(this.journalArticle.getIssue() + "");
        this.oldYear.setText(this.journalArticle.getYear() + "");
        this.oldPages.setText(this.journalArticle.getPages());
        this.oldDoi.setText(this.journalArticle.getDoi());
    }

    private void populateNewValues() {
        if(this.referenceChanges.length > 0){
            this.newTitle.setText( this.referenceChanges[0]);
            this.newAuthors.setText( this.referenceChanges[1].replaceAll("; ", "\n"));
            this.newJournal.setText( this.referenceChanges[2]);
            this.newJournalShortForm.setText( this.referenceChanges[3]);
            this.newVolume.setText( this.referenceChanges[4]);
            this.newIssue.setText( this.referenceChanges[5]);
            this.newYear.setText( this.referenceChanges[6]);
            this.newPages.setText( this.referenceChanges[7]);
            this.newDoi.setText( this.referenceChanges[8]);
        }
    }

    private String[] getValuesFromJson(JSONObject webResponse) {
        try {
            String title = webResponse.getString("title");
            String authors = getAuthorListFromJsonArray(webResponse.getJSONArray("authors"));
            String journal = webResponse.getString("fulljournalname");
            String journalShortForm = webResponse.getString("source");
            String volume = webResponse.getString("volume");
            String issue = webResponse.getString("issue");
            String year = webResponse.getString("pubdate").substring(0,4);
            String pages = webResponse.getString("pages");
            String doi = searchArticleIdsForDoi(webResponse.getJSONArray("articleids"));

            return new String[]{title, authors, journal, journalShortForm, volume, issue, year, pages, doi};
        } catch (JSONException e){
            e.printStackTrace();

            return new String[]{};
        }
    }

    private String getAuthorListFromJsonArray(JSONArray authorsAsJsonArray) throws JSONException {
        StringBuilder authorsAsString = new StringBuilder();

        int authorsLength = authorsAsJsonArray.length();

        for(int index = 0; index < authorsLength; index++){
            JSONObject jo = authorsAsJsonArray.getJSONObject(index);

            String author = jo.getString("name").replaceFirst(" ", ", ");
            authorsAsString.append(author).append("; ");
        }

        return authorsAsString.toString();
    }

    private String searchArticleIdsForDoi(JSONArray articleids) throws JSONException {
        String doi = "";

        int lengthJarray = articleids.length();
        for(int index = 0; index < lengthJarray; index++){
            JSONObject jo = articleids.getJSONObject(index);

            if(jo.getString("idtype").equals("doi")){
                doi = jo.getString("value");
            }
        }

        return doi;
    }

    private void updateReference(){
        if (checkTitle.isSelected()){ this.journalArticle.setTitle(this.newTitle.getText());}

        if (checkAuthors.isSelected()){
            this.journalArticle.setAuthors(this.newAuthors.getText().replaceAll("\n","; "));
        }

        if (checkJournal.isSelected()){ this.journalArticle.setJournal(this.newJournal.getText());}

        if (checkJournalShortForm.isSelected()){ this.journalArticle.setJournalShortForm(this.newJournalShortForm.getText());}

        if (checkVolume.isSelected()){
            int volume = MyLittleHelpers.convertStringInputToInteger(this.newVolume.getText());
            this.journalArticle.setVolume(volume);
        }

        if (checkIssue.isSelected()){
            int issue = MyLittleHelpers.convertStringInputToInteger(this.newIssue.getText());
            this.journalArticle.setIssue(issue);
        }

        if (checkYear.isSelected()){
            int year = MyLittleHelpers.convertStringInputToInteger(this.newYear.getText());
            this.journalArticle.setYear(year);
        }

        if (checkPages.isSelected()){ this.journalArticle.setPages(this.newPages.getText());}

        if (checkDoi.isSelected()){ this.journalArticle.setDoi(this.newDoi.getText());}
    }
}
