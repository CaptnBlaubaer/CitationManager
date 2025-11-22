package de.apaschold.demo.gui;

import de.apaschold.demo.HelloApplication;
import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.logic.CitationManager;
import de.apaschold.demo.logic.databasehandling.SqlWriter;
import de.apaschold.demo.logic.filehandling.TextFileHandler;
import de.apaschold.demo.model.AbstractCitation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * <h2>GuiController</h2>
 * <li>Singleton class that manages the views of the application and holds references to the main data structures.</li>
 * <li>Starting point of application</li>
 * <li>Last used librabry file path is stored in a .txt file in the resource folder</li>
 */

public class GuiController {
    //0. constants

    //1. attributes
    private static GuiController instance;
    private Stage mainStage;

    //2. constructors

    /**
     * Private constructor for singleton pattern.
     * Loads the last used library file path from a .txt and initializes the {@link CitationManager}.
     * If the library is empty, sets the active library file path to the program directory.
     */
    private GuiController() {}

    public static synchronized GuiController getInstance() {
        if (instance == null) {
            instance = new GuiController();
        }
        return instance;
    }

    //3. getter and setter methods
    public void setMainStage(Stage mainStage) { this.mainStage = mainStage;}

    //4. open view methods
    /**
     * <h2>loadMainMenu</h2>
     * Loads the main menu view of the application.
     */
    public void loadMainMenu() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1680, 960);
            this.mainStage.setTitle("Citation Manager");
            this.mainStage.setScene(scene);
            this.mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h2>loadAddNewCitationView</h2>
     * Loads the view for adding a new {@link AbstractCitation}.
     */
    public void loadAddNewCitationView() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-new-citation-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 320, 500);
            Stage newCitationStage = new Stage();
            newCitationStage.setTitle("Add New Citation");
            newCitationStage.setScene(scene);
            newCitationStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h2>loadReferenceUpdateView</h2>
     * Loads the view for updating {@link AbstractCitation} from PubMed.
     */
    public void loadReferenceUpdateView() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reference-update-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
            Stage citationUpdateStage = new Stage();
            citationUpdateStage.setTitle("Reference update from PubMed");
            citationUpdateStage.setScene(scene);
            citationUpdateStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h2>loadImportFromBibTexView</h2>
     * Loads the view for importing {@link AbstractCitation} from a BibTex file.
     */
    public void loadImportFromBibTexView() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("import-from-bibtex-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 320, 250);
            Stage bibTexImportStage = new Stage();
            bibTexImportStage.setTitle("Import from BibTex");
            bibTexImportStage.setScene(scene);
            bibTexImportStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h2>loadCreateNewLibraryView</h2>
     * Loads the view for creating a new empty {@link CitationManager}.
     */
    public void loadCreateNewLibraryView() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-new-library-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 300, 130);
            Stage newCitationLibraryStage = new Stage();
            newCitationLibraryStage.setTitle("Create empty library");
            newCitationLibraryStage.setScene(scene);
            newCitationLibraryStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
