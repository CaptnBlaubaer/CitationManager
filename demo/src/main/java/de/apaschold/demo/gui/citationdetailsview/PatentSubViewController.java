package de.apaschold.demo.gui.citationdetailsview;

import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.model.Patent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;

/**
 * <h2>PatentSubViewController</h2>
 * <li>Controller for the patent sub view in the article view.</li>
 * <li>Handles displaying and editing patent specific information.</li>
 */
public class PatentSubViewController implements Initializable {
    //0. constants

    //1. attributes
    private Patent patent;

    //2. FXML elements
    @FXML
    private TextField titleChange;
    @FXML
    private TextArea authorsChange;
    @FXML
    private TextField yearChange;
    @FXML
    private TextField urlChange;

    //3. constructors/initialize method
    /** <h2>initialize</h2>
     * <li>Initializes the controller by populating the patent sub view with the selected patent's data.</li>
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        this.patent = (Patent) GuiController.getInstance().getSelectedCitation();

        populateEditSubView();
    }

    //4. FXML methods
    /** <h2>saveChanges</h2>
     * <li>Saves the changes made in the edit view to the selected {@link Patent}.</li>
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void saveChanges() throws IOException {
        if (this.patent != null){
            this.patent.setTitle( this.titleChange.getText());
            this.patent.setAuthors( this.authorsChange.getText().replace("\n", "; "));

            this.patent.setYear( MyLittleHelpers.convertStringInputToInteger(this.yearChange.getText()));

            this.patent.setDoi(this.urlChange.getText());

            //update the labels in the article overview
            GuiController.getInstance().loadMainMenu();
        }
    }

    //5. other methods
    /** <h2>populateEditSubView</h2>
     * <li>Populates the edit sub view with the selected {@link Patent} data.</li>
     */
    public void populateEditSubView() {
        String yearAsString = "-";
        if (this.patent.getYear() != 0) {
            yearAsString = String.valueOf(this.patent.getYear());
        }

        this.titleChange.setText(this.patent.getTitle());
        this.authorsChange.setText(this.patent.getAuthor().replace("; ", "\n"));
        this.yearChange.setText(yearAsString);
        this.urlChange.setText(this.patent.getDoi());
    }
}