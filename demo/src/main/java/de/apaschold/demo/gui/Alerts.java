package de.apaschold.demo.gui;

import de.apaschold.demo.additionals.AppTexts;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts {
    //0. constants

    //1. attributes

    //2. contructor
    private Alerts(){}

    //3. alerts
    public static void showAlertMessageEmptyLibrary() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(AppTexts.ALERT_EMPTY_LIBRARY_TITLE);
        alert.setHeaderText(AppTexts.ALERT_EMPTY_LIBRARY_HEADER);
        alert.show();
    }

    public static void showAlertInvalidFileName() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(AppTexts.ALERT_INVALID_FILENAME_TITLE);
        alert.setHeaderText(AppTexts.ALERT_INVALID_FILENAME_HEADER);
        alert.show();
    }

    public static void showAlertFileNameAlreadyExists() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(AppTexts.ALERT_FILENAME_EXISTS_TITLE);
        alert.setHeaderText(AppTexts.ALERT_FILENAME_EXISTS_HEADER);
        alert.show();
    }

    //4. information
    public static void showInformationNoFileChoosen(){
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(AppTexts.INFORMATION_NO_FILE_CHOSEN_TITLE);
        alert.setHeaderText(AppTexts.INFORMATION_NO_FILE_CHOSEN_HEADER);
        alert.show();
    }

    //5. conformation
    public static Optional<ButtonType> showConfirmationDeleteArticle() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(AppTexts.CONFIRMATION_DELETE_ARTICLE_TITLE);
        alert.setHeaderText(AppTexts.CONFIRMATION_DELETE_ARTICLE_CONTENT);
        return alert.showAndWait();
    }
}
