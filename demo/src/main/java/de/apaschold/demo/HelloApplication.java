package de.apaschold.demo;

import de.apaschold.demo.gui.GuiController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


/*
  TODO delete pdf file, when citation is deleted
  TODO add grouping functionality
 */


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GuiController.getInstance().setMainStage(stage);
        GuiController.getInstance().initializeLibrary();
        GuiController.getInstance().loadMainMenu();
    }

    public static void main(String[] args) {
        launch();
    }
}