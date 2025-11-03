package de.apaschold.demo.gui;

import de.apaschold.demo.HelloApplication;
import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.logic.CitationLibrary;
import de.apaschold.demo.logic.databasehandling.SqlReader;
import de.apaschold.demo.logic.databasehandling.SqlWriter;
import de.apaschold.demo.logic.filehandling.TextFileHandler;
import de.apaschold.demo.model.Citation;
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
    private CitationLibrary library;
    private Citation selectedCitation;
    private Citation dummyCitationToEdit;
    private String activeLibraryTableName;
    private String activeLibraryFilePath;
    private JSONObject referenceChanges;

    //2. constructors

    /**
     * Private constructor for singleton pattern.
     * Loads the last used library file path from a .txt and initializes the {@link CitationLibrary}.
     * If the library is empty, sets the active library file path to the program directory.
     */
    private GuiController() {
        this.activeLibraryFilePath = TextFileHandler.getInstance().loadLibraryFilePath();

        this.activeLibraryTableName = AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS;
    }

    public static synchronized GuiController getInstance() {
        if (instance == null) {
            instance = new GuiController();
        }
        return instance;
    }

    //3. getter and setter methods
    public void setMainStage(Stage mainStage) { this.mainStage = mainStage;}

    public List<Citation> getCitationList() { return this.library.getCitations();}

    public Citation getSelectedCitation() { return this.selectedCitation;}

    public void setSelectedCitation(Citation selectedCitation) { this.selectedCitation = selectedCitation;}

    public Citation getDummyCitationToEdit() { return this.dummyCitationToEdit;}

    public void setDummyCitationToEdit(Citation dummyCitationToEdit) { this.dummyCitationToEdit = dummyCitationToEdit;}

    public String getActiveLibraryFilePath() { return this.activeLibraryFilePath;}

    public void setActiveLibraryFilePath(String activeLibraryFilePath) { this.activeLibraryFilePath = activeLibraryFilePath;}

    public JSONObject getReferenceChangesAsJsonObject(){ return this.referenceChanges;}

    public void setReferenceChanges(JSONObject referenceChanges){ this.referenceChanges = referenceChanges;}

    //4. open view methods
    public void initializeLibrary(){
        this.library = new CitationLibrary(this.activeLibraryTableName);

        this.selectedCitation = this.library.getFirstCitation();
    }

    /**
     * <h2>loadMainMenu</h2>
     * Loads the main menu view of the application.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    public void loadMainMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1680, 960);
        this.mainStage.setTitle("Citation Manager");
        this.mainStage.setScene(scene);
        this.mainStage.show();
    }

    /**
     * <h2>loadAddNewCitationView</h2>
     * Loads the view for adding a new {@link Citation}.
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
     * Loads the view for updating {@link Citation} from PubMed.
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
     * Loads the view for importing {@link Citation} from a BibTex file.
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
     * Loads the view for creating a new empty {@link CitationLibrary}.
     */
    public void loadCreateNewLibraryView() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-new-library-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 300, 130);
            Stage newCitationLibraryStage = new Stage();
            newCitationLibraryStage.setTitle("Create empty library");
            newCitationLibraryStage.setScene(scene);
            newCitationLibraryStage.showAndWait();

            this.library.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //5. other
    /**
     * <h2>changeActiveLibrary</h2>
     * <li>Changes the active {@link CitationLibrary} to the specified file.</li>
     * <li>Creates a new library table in the SQL database if it does not exist.</
     *
     * @param filePath the file path of the new library file
     */
    public void changeActiveLibraryFile(String filePath){
        this.activeLibraryFilePath = filePath;
        this.activeLibraryTableName = AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS;

        SqlWriter.createNewLibraryDatabase();
        SqlWriter.createNewLibraryTableSqlite(this.activeLibraryTableName);
    }

    /**
     * <h2>exportActiveLibraryToBibTex</h2>
     * Exports the active {@link CitationLibrary} to a BibTex file.
     * The BibTex file is created in the same directory as the active library file,
     * with the same name but with a .bib extension.
     *
     * @throws NullPointerException if the library is empty and there is nothing to export
     */
    public void exportActiveLibraryToBibTex(){

        String bibTexString = this.library.generateStringForBibTex();

        if(!bibTexString.isEmpty()) {
            String bibTexFilePath = this.activeLibraryFilePath.replace(AppTexts.LIBRARY_FILE_FORMAT, AppTexts.BIBTEX_FILE_FORMAT);

            TextFileHandler.getInstance().exportLibraryToBibTex(bibTexString, bibTexFilePath);
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * <h2>saveActiveLibraryToCml</h2>
     * Saves the current state of the active {@link CitationLibrary} to the CML file.
     */
    public void saveActiveLibraryToCml(){
        TextFileHandler.getInstance().exportLibraryToCml(this.library.getCitations(), this.activeLibraryFilePath);
    }

    /**
     * <h2>openLibraryFile</h2>
     * <li>Opens new {@link CitationLibrary} file from selected Path.</li>
     *
     * @param chosenLibraryFilePath the file path of the library file to import from
     */
    public void openLibraryFile(String chosenLibraryFilePath){
        this.activeLibraryFilePath = chosenLibraryFilePath;
        this.activeLibraryTableName = AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS;

        this.library.refreshLibraryFromDatabase(this.activeLibraryTableName);
    }

    /**
     * <h2>deleteSelectedCitation</h2>
     * Deletes the currently selected {@link Citation} from the active {@link CitationLibrary}.
     */
    public void deleteSelectedCitation() {
        SqlWriter.deleteCitationFromLibrary(this.activeLibraryTableName, this.selectedCitation);

        this.library.refreshLibraryFromDatabase(this.activeLibraryTableName);
        saveActiveLibraryToCml();
    }

    /** <h2>addNewAttachmentToCitationReference</h2>
     * <li>Adds a new attachment to the currently selected {@link Citation}.</li>
     *
     * @param newAttachment the file path of the new attachment to add
     */
    public void addNewAttachmentToCitationReference(String newAttachment) {
        this.selectedCitation.addNewAttachment(newAttachment);

        SqlWriter.updateCitationInLibrary(this.activeLibraryTableName, this.selectedCitation);
        saveActiveLibraryToCml();
    }

    /** <h2>updateLibraryWithEditedCitation</h2>
     * <li>Updates the active {@link CitationLibrary} with the changes made to the selected {@link Citation}.</li>
     *
     * @param editedCitation the edited {@link Citation} to update in the library
     */
    public void updateLibraryWithEditedCitation(Citation editedCitation) {
        SqlWriter.updateCitationInLibrary(this.activeLibraryTableName, editedCitation);

        this.library.refreshLibraryFromDatabase(this.activeLibraryTableName);
        saveActiveLibraryToCml();

        setSelectedCitation(editedCitation);
    }

    public void addCitationToLibrary(Citation newCitation) {
        SqlWriter.addNewCitationToLibraryTable(this.activeLibraryTableName, newCitation);

        this.library.refreshLibraryFromDatabase(this.activeLibraryTableName);
        saveActiveLibraryToCml();
    }
}
