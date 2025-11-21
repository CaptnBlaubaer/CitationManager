package de.apaschold.demo.gui;

import de.apaschold.demo.logic.CitationFactory;
import de.apaschold.demo.logic.CitationManager;
import de.apaschold.demo.model.AbstractCitation;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * <h2>ImportFromBibTexViewController</h2>
 * <li>Controller for the import from BibTex view.</li>
 * <li>Handles importing {@link AbstractCitation} from BibTex formatted text input.</li>
 */

public class ImportFromBibTexViewController {
    //0. constants

    //1. attributes

    //2. FXML elements
    @FXML
    private TextArea bibTexInput;

    //3. contructor/initializemethod

    //4.FXML methods
    /** <h2>importButtonClick</h2>
     * <li>Imports {@link AbstractCitation} from the BibTex text input and closes the window.</li>
     */
    @FXML
    protected void importButtonClick() {
        String bibTexText = bibTexInput.getText();

        if (!bibTexText.isEmpty()) {
            importBibTex(bibTexText);
        }

        Stage stage = (Stage) bibTexInput.getScene().getWindow();
        stage.close();
    }

    //5. other methods
    /** <h2>importBibTex</h2>
     * <li>Parses the BibTex formatted text and adds the corresponding {@link AbstractCitation} to the {@link CitationManager}.</li>
     * <li>Creates {@link AbstractCitation} through {@link CitationFactory}</li>
     * @param bibTexText The BibTex formatted text input.
     */
    private void importBibTex(String bibTexText) {
        String[] separatedImports = bibTexText.replace("@", "!!!!!@").split("!!!!!");

        for (String singleImport : separatedImports) {
            if (!singleImport.isEmpty()) {
                AbstractCitation importedCitation = CitationFactory.createCitationFromBibTex(singleImport);
                GuiController.getInstance().addCitationToLibrary(importedCitation);
            }
        }
    }
}
