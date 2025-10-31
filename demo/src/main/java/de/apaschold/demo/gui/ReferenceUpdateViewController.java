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

/**
 * <h2>ReferenceUpdateViewController</h2>
 * <li>Controller for the reference update view.</li>
 * <li>Handles displaying old and new reference values and updating the reference based on user selection.</li>
 * <li>Only available for journal articles</li>
 */

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
    /**
     * <h2>initialize</h2>
     * <li>Initializes the controller by populating old and new reference values.</li>
     *
     * @param location          the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle    the resources used to localize the root object, or null if the root object was not localized
     */

    @Override
    public void initialize (URL location, ResourceBundle resourceBundle){
        this.referenceChanges =
                getValuesFromJson(GuiController.getInstance().getReferenceChangesAsJsonObject());

        this.journalArticle = (JournalArticle) GuiController.getInstance().getSelectedCitation();

        populateOldValues();

        populateNewValues();
    }

    //4. FXML methods
    /**
     * <h2>confirmChanges</h2>
     * <li>Updates the reference with the selected new values and closes the update view.</li>
     */

    @FXML
    protected void confirmChanges() {
        updateReference();
        GuiController.getInstance().updateLibraryWithEditedCitation(this.journalArticle);

        Stage stage = (Stage) this.checkAuthors.getScene().getWindow();

        stage.close();
    }

    /**
     * <h2>discardChanges</h2>
     * <li>Closes the update view without making any changes to the reference.</li>
     */

    @FXML
    protected void discardChanges(){
        Stage stage = (Stage) this.checkAuthors.getScene().getWindow();

        stage.close();
    }

    /** <h2>populateOldValues</h2>
     * <li>Fills TextFields with old reference values</li>
     */
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

    /** <h2>populateNewValues</h2>
     * <li>Fills TextFields with new reference values retrieved from pubMed database</li>
     */
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

    /** <h2>getValuesFromJson</h2>
     * <li>Extracts reference values from a JSON object.</li>
     *
     * @param webResponse  the JSON object containing reference data
     * @return             an array of reference values
     */
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

    /** <h2>getAuthorListFromJsonArray</h2>
     * <li>Converts a JSON array of authors into a formatted string.</li>
     *
     * @param authorsAsJsonArray  the JSON array containing author data
     * @return                    a formatted string of authors
     * @throws JSONException      if there is an error parsing the JSON array
     */

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

    /** <h2>searchArticleIdsForDoi</h2>
     * <li>Searches a JSON array for a DOI and returns its value.</li>
     *
     * @param articleids       the JSON array containing article IDs
     * @return                 the DOI value if found, otherwise an empty string
     * @throws JSONException   if there is an error parsing the JSON array
     */
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

    /** <h2>updateReference</h2>
     * <li>Updates the journal article reference with new values based on user selection.</li>
     */
    private void updateReference(){
        if (checkTitle.isSelected()){ this.journalArticle.setTitle(this.newTitle.getText());}

        //strip to remove leading/trailing whitespace characters, e.g. \n
        if (checkAuthors.isSelected()){
            this.journalArticle.setAuthors(this.newAuthors.getText().strip().replaceAll("\n","; "));
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
