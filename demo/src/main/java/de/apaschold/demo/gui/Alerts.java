package de.apaschold.demo.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * <h2>Alerts</h2>
 * <li>Utility class for displaying alert, information, and confirmation dialogs.</li>
 */

public class Alerts {
    //0. constants
    public static final String ALERT_EMPTY_LIBRARY_TITLE = "Library is empty";
    public static final String ALERT_EMPTY_LIBRARY_HEADER = "The active library is empty.";
    public static final String ALERT_INVALID_FILENAME_TITLE = "Invalid filename";
    public static final String ALERT_INVALID_FILENAME_HEADER = "Filename can't contain whitespace character and can't be empty";
    public static final String ALERT_FILENAME_EXISTS_TITLE = "Filename already exists";
    public static final String ALERT_FILENAME_EXISTS_HEADER = "Filename already exists in this directory";
    public static final String INFORMATION_NO_FILE_CHOSEN_TITLE = "No file chosen";
    public static final String INFORMATION_NO_FILE_CHOSEN_HEADER = "There was no file chosen";
    public static final String CONFIRMATION_DELETE_CITATION_TITLE = "Delete selected article";
    public static final String CONFIRMATION_DELETE_CITATION_CONTENT = "Do you want to delete selected article?\nOK to confirm";
    public static final String INFORMATION_RECORD_NOT_FOUND_TITLE = "Record not found";
    public static final String INFORMATION_RECORD_NOT_FOUND_HEADER = "The record wasn't found in the PubMed database.";
    public static final String INFORMATION_FILE_NOT_FOUND_TITLE = "Attachment not found";
    public static final String INFORMATION_FILE_NOT_FOUND_HEADER = "The attachment doesn't exist.";

    //1. attributes

    //2. contructor
    private Alerts(){}

    //3. alerts
    public static void showAlertMessageEmptyLibrary() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ALERT_EMPTY_LIBRARY_TITLE);
        alert.setHeaderText(ALERT_EMPTY_LIBRARY_HEADER);
        alert.show();
    }

    public static void showAlertInvalidFileName() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ALERT_INVALID_FILENAME_TITLE);
        alert.setHeaderText(ALERT_INVALID_FILENAME_HEADER);
        alert.show();
    }

    public static void showAlertFileNameAlreadyExists() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ALERT_FILENAME_EXISTS_TITLE);
        alert.setHeaderText(ALERT_FILENAME_EXISTS_HEADER);
        alert.show();
    }

    //4. information
    public static void showInformationNoFileChoosen(){
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(INFORMATION_NO_FILE_CHOSEN_TITLE);
        alert.setHeaderText(INFORMATION_NO_FILE_CHOSEN_HEADER);
        alert.show();
    }

    //5. conformation
    public static Optional<ButtonType> showConfirmationDeleteCitation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(CONFIRMATION_DELETE_CITATION_TITLE);
        alert.setHeaderText(CONFIRMATION_DELETE_CITATION_CONTENT);
        return alert.showAndWait();
    }

    public static void showInformationRecordNotFound() {
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(INFORMATION_RECORD_NOT_FOUND_TITLE);
        alert.setHeaderText(INFORMATION_RECORD_NOT_FOUND_HEADER);
        alert.show();
    }

    public static void showInformationFileNotFoundInFolder(String fileName) {
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(INFORMATION_FILE_NOT_FOUND_TITLE);
        alert.setHeaderText(INFORMATION_FILE_NOT_FOUND_HEADER);
        alert.show();
    }
}
