package de.apaschold.demo.gui;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.logic.filehandling.TextFileHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class NewLibraryViewController implements Initializable {
    //0. constants

    //1. attributes
    private String folderPath;

    //2. FXML elements
    @FXML
    private TextField newLibraryName;
    @FXML
    private Label folderPathLabel;

    //3.constructor/initialize method
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle){
        this.folderPath = GuiController.getInstance().getActiveLibraryFilePath().replaceAll("\\\\[a-z]+\\.cml","");

        this.folderPathLabel.setText(this.folderPath);
    }

    //4. FXML methods
    @FXML
    protected void createNewLibrary(){
        String fileName = this.newLibraryName.getText();

        String filePath = folderPath + "\\" + fileName + ".cml";

        if (!fileName.contains(" ") && !fileName.isEmpty()){
            File newLibraryFile = new File(filePath);

            if (!newLibraryFile.exists()) {
                TextFileHandler.getInstance().createEmptyLibrary(newLibraryFile);
                TextFileHandler.getInstance().saveNewActiveLibraryPath(filePath);
                GuiController.getInstance().setActiveLibraryFilePath(filePath);
            } else {
                showAlertFileNameAlreadyExists();
            }
        } else {
            showAlertInavlidFileName();
        }

        Stage stage = (Stage) this.newLibraryName.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void changeFolderPath(){
        Stage stage = (Stage) this.newLibraryName.getScene().getWindow();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("");
        directoryChooser.setInitialDirectory(new File(this.folderPath));
        File selectedDirectory = directoryChooser.showDialog(stage);

        this.folderPath = selectedDirectory.getAbsolutePath();
        this.folderPathLabel.setText(selectedDirectory.getAbsolutePath());
    }

    //5. other Methods
    private void showAlertInavlidFileName() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(AppTexts.ALERT_INVALID_FILENAME_TITLE);
        alert.setHeaderText(AppTexts.ALERT_INVALID_FILENAME_CONTENT);
        alert.show();
    }

    private void showAlertFileNameAlreadyExists() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(AppTexts.ALERT_FILENAME_EXISTS_TITLE);
        alert.setHeaderText(AppTexts.ALERT_FILENAME_EXISTS_CONTENT);
        alert.show();
    }
}
