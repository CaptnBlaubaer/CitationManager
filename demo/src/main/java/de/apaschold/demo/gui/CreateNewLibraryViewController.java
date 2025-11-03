package de.apaschold.demo.gui;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.logic.filehandling.FileHandler;
import de.apaschold.demo.logic.filehandling.TextFileHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h2>CreateNewLibraryViewController</h2>
 * <li>Controller for the create new library view.</li>
 * <li>Handles creating a new {@link de.apaschold.demo.logic.CitationLibrary} file in the specified folder path.</li>
 */

public class CreateNewLibraryViewController implements Initializable {
    //0. constants

    //1. attributes
    private String folderPath;

    //2. FXML elements
    @FXML
    private TextField newLibraryName;
    @FXML
    private Label folderPathLabel;

    //3.constructor/initialize method
    /** <h2>initialize</h2>
     * <li>Initializes the controller by setting the folder path label to the current active library's folder path.</li>
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle){
        this.folderPath = GuiController.getInstance().getActiveLibraryFilePath().replaceAll(AppTexts.REGEX_REPLACE_DB_FILENAME,"");

        System.out.println(this.folderPath);

        this.folderPath = "C:\\Users\\Hein\\Desktop\\Programmierstuff\\JAva\\CitationManager";

        this.folderPathLabel.setText(this.folderPath);
    }

    //4. FXML methods
    /** <h2>createNewLibrary</h2>
     * <li>Creates a new {@link de.apaschold.demo.logic.CitationLibrary} file and a folder for the pdfs with the specified name in the selected folder path.</li>
     * <li>Updates the active library file path in the GuiController and saves it to a .txt file.</li>
     * <li>Closes the create new library view.</li>
     */

    @FXML
    protected void createNewLibrary(){
        String fileName = this.newLibraryName.getText();

        String filePath = folderPath + "\\" + fileName + AppTexts.LIBRARY_FILE_FORMAT;

        if (!fileName.contains(" ") && !fileName.isEmpty()){
            File newLibraryFile = new File(filePath);

            if (!newLibraryFile.exists()) {
                FileHandler.getInstance().createEmptyLibrary(newLibraryFile);

                TextFileHandler.getInstance().saveNewActiveLibraryPath(filePath);

                GuiController.getInstance().changeActiveLibraryFile(filePath);
            } else {
                Alerts.showAlertFileNameAlreadyExists();
            }
        } else {
            Alerts.showAlertInvalidFileName();
        }

        Stage stage = (Stage) this.newLibraryName.getScene().getWindow();
        stage.close();
    }

    /** <h2>changeFolderPath</h2>
     * <li>Opens a directory chooser dialog to select a new folder path for the new library.</li>
     * <li>Updates the folder path label with the selected folder path.</li>
     */

    @FXML
    protected void changeFolderPath() {
        Stage stage = (Stage) this.newLibraryName.getScene().getWindow();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("");
        directoryChooser.setInitialDirectory(new File(this.folderPath));

        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory.exists()) {
            this.folderPath = selectedDirectory.getAbsolutePath();
            this.folderPathLabel.setText(selectedDirectory.getAbsolutePath());
        } else {
            Alerts.showInformationNoFolderChosen();
        }
    }
    //5. other Methods

}
