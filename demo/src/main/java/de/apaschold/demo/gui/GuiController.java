package de.apaschold.demo.gui;

import de.apaschold.demo.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiController {
    //0. constants

    //1. attributes
    private static GuiController instance;
    private Stage mainStage;

    //2. constructors
    private GuiController() {
    }

    public static synchronized GuiController getInstance() {
        if (instance == null) {
            instance = new GuiController();
        }
        return instance;
    }

    //3. getter and setter methods
    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    //4. open view methods
    public void loadMainMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        this.mainStage.setTitle("Citation Manager");
        this.mainStage.setScene(scene);
        this.mainStage.show();
    }
}
