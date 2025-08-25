package de.apaschold.demo;

import de.apaschold.demo.gui.GuiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/*TODO create model class article
  TODO create overview Window
  TODO create CSV storage for article information
  TODO create view to open and article pdf files
 */


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GuiController.getInstance().setMainStage(stage);
        GuiController.getInstance().loadMainMenu();
    }

    public static void main(String[] args) {
        launch();
    }
}