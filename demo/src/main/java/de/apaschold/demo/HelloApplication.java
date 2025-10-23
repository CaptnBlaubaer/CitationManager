package de.apaschold.demo;

import de.apaschold.demo.gui.GuiController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


/*TODO create model website
  TODO create view to add other article types (book, web, etc.)
  TODO obtain String representation of article types in add-new-article-view
  TODO export functionality to bibtex
  TODO import of empty fields cause problems
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