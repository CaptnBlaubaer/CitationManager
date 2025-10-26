package de.apaschold.demo.gui.citationdetailsview;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.PhdThesis;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * <h2>PhdThesisSubViewController</h2>
 * <li>Controller for the PhD thesis sub view in the main view.</li>
 * <li>Handles displaying and editing PhD thesis article details.</li>
 */
public class PhdThesisSubViewController implements Initializable {
    //0. constants

    //1. attributes
    private PhdThesis phdThesis;

    //2. FXML elements
    @FXML
    private TextField titleChange;
    @FXML
    private TextArea authorsChange;
    @FXML
    private TextField yearChange;
    @FXML
    private TextField doiChange;

    //3. constructors/initialize method
    /** <h2>initialize</h2>
     * <li>Initializes the controller by populating the edit view with the selected {@link PhdThesis} details.</li>
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        this.phdThesis = (PhdThesis) GuiController.getInstance().getSelectedArticle();

        populateEditView();
    }

    //4. FXML methods
    /** <h2>saveChanges</h2>
     * <li>Saves the changes made to the {@link PhdThesis} details.</li>
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void saveChanges() throws IOException {
        if (this.phdThesis != null){
            this.phdThesis.setTitle( this.titleChange.getText());
            this.phdThesis.setAuthors( this.authorsChange.getText().replace("\n", ", "));

            this.phdThesis.setYear( MyLittleHelpers.convertStringInputToInteger(this.yearChange.getText()));

            this.phdThesis.setDoi(this.doiChange.getText());

            //update the labels in the article overview
            GuiController.getInstance().loadMainMenu();
        }
    }

    //5. other methods
    /** <h2>populateEditTab</h2>
     * <li>Populates the edit view with the {@link PhdThesis} details.</li>
     */
    public void populateEditView(){
        String yearAsString = "-";
        if (this.phdThesis.getYear() != 0){
            yearAsString = String.valueOf(this.phdThesis.getYear());
        }

        this.titleChange.setText(this.phdThesis.getTitle());
        this.authorsChange.setText(this.phdThesis.getAuthor().replace("; ", "\n"));
        this.yearChange.setText(yearAsString);
        this.doiChange.setText(this.phdThesis.getDoi());
    }
}