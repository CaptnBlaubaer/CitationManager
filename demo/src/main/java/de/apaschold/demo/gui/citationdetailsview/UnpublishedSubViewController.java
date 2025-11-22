package de.apaschold.demo.gui.citationdetailsview;

import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.logic.MainViewModel;
import de.apaschold.demo.model.AbstractCitation;
import de.apaschold.demo.model.Unpublished;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;

/**
 * <h2>UnpublishedSubViewController</h2>
 * <li>Controller for the unpublished article sub view.</li>
 * <li>Handles displaying and editing unpublished article details
 * as well as displaying attached PDF files.</li>
 */

public class UnpublishedSubViewController implements Initializable {
    //0. constants

    //1. attributes
    private Unpublished unpublished;

    //2. FXML elements
    @FXML
    private TextField titleChange;
    @FXML
    private TextArea authorsChange;
    @FXML
    private TextField yearChange;

    //3. constructors/initialize method
    /** <h2>initialize</h2>
     * <li>Initializes the controller by populating the {@link Unpublished} details</li>
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        this.unpublished = (Unpublished) MainViewModel.getInstance().getDummyCitation();

        populateEditView();
    }

    //4. FXML methods
    /** <h2>saveChanges</h2>
     * <li>Saves changes made to the book section details back to the dummy {@link AbstractCitation}</li>
     * <li>Sets the updated citation as the selected citation in the {@link GuiController}.</li>
     * <li>Updates the main menu view.</li>
     * @throws IOException if an I/O error occurs while loading the main menu.
     */

    @FXML
    private void saveChanges() throws IOException {
        if (this.unpublished != null){
            this.unpublished.setTitle( this.titleChange.getText());
            this.unpublished.setAuthors( this.authorsChange.getText().replace("\n", "; "));

            this.unpublished.setYear( MyLittleHelpers.convertStringInputToInteger(this.yearChange.getText()));

            MainViewModel.getInstance().updateCitationInDatabase(this.unpublished);

            //update the labels in the article overview
            GuiController.getInstance().loadMainMenu();

        }
    }

    //5. other methods
    /** <h2>populateEditView</h2>
     * <li>Populates the edit view with the details of the selected {@link Unpublished}.</li>
     */
    public void populateEditView(){
        String yearAsString = "-";
        if (this.unpublished.getYear() != 0){
            yearAsString = String.valueOf(this.unpublished.getYear());
        }

        this.titleChange.setText(this.unpublished.getTitle());
        this.authorsChange.setText(this.unpublished.getAuthor().replace("; ", "\n"));
        this.yearChange.setText(yearAsString);
    }
}
