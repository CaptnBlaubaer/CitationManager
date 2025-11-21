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
    private CitationManager library;
    private AbstractCitation selectedCitation;
    private AbstractCitation dummyCitationToEdit;
    private String activeLibraryTableName;
    private String activeLibraryFilePath;
    private JSONObject referenceChanges;

    //2. constructors

    /**
     * Private constructor for singleton pattern.
     * Loads the last used library file path from a .txt and initializes the {@link CitationManager}.
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

    public List<AbstractCitation> getCitationList() { return this.library.getCitations();}

    public AbstractCitation getSelectedCitation() { return this.selectedCitation;}

    public void setSelectedCitation(AbstractCitation selectedCitation) { this.selectedCitation = selectedCitation;}

    public AbstractCitation getDummyCitationToEdit() { return this.dummyCitationToEdit;}

    public void setDummyCitationToEdit(AbstractCitation dummyCitationToEdit) { this.dummyCitationToEdit = dummyCitationToEdit;}

    public String getActiveLibraryFilePath() { return this.activeLibraryFilePath;}

    public void setActiveLibraryFilePath(String activeLibraryFilePath) { this.activeLibraryFilePath = activeLibraryFilePath;}

    public JSONObject getReferenceChangesAsJsonObject(){ return this.referenceChanges;}

    public void setReferenceChanges(JSONObject referenceChanges){ this.referenceChanges = referenceChanges;}

    //4. open view methods
    public void initializeLibrary(){
        this.library = new CitationManager(this.activeLibraryTableName);

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

            this.library.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //5. other
    /**
     * <h2>changeActiveLibrary</h2>
     * <li>Changes the active {@link CitationManager} to the specified file.</li>
     * <li>Creates a new library database if it does not exist.</
     *
     * @param filePath the file path of the new library file
     */
    public void createNewLibrary(String filePath){
        this.activeLibraryFilePath = filePath;
        this.activeLibraryTableName = AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS;

        SqlWriter.createNewLibraryDatabase();
        SqlWriter.createNewLibraryTable(this.activeLibraryTableName);
    }

    /**
     * <h2>exportActiveLibraryToBibTex</h2>
     * Exports the active {@link CitationManager} to a BibTex file.
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
     * <h2>openLibraryFile</h2>
     * <li>Opens new {@link CitationManager} file from selected Path.</li>
     *
     * @param chosenLibraryFilePath the file path of the library file to import from
     */
    public void openLibraryFile(String chosenLibraryFilePath){
        this.activeLibraryFilePath = chosenLibraryFilePath;
        this.activeLibraryTableName = AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS;

        this.library.refreshLibraryFromDatabase(this.activeLibraryTableName);
    }


    public void deleteSelectedCitation() {
        SqlWriter.deleteCitationFromLibrary(this.activeLibraryTableName, this.selectedCitation);

        this.library.refreshLibraryFromDatabase(this.activeLibraryTableName);
    }

    /** <h2>addNewAttachmentToCitationReference</h2>
     * <li>Adds a new attachment to the currently selected {@link AbstractCitation}.</li>
     *
     * @param newAttachment the file path of the new attachment to add
     */
    public void addNewAttachmentToCitationReference(String newAttachment) {
        this.selectedCitation.addNewAttachment(newAttachment);

        SqlWriter.updateCitationInLibrary(this.activeLibraryTableName, this.selectedCitation);
    }

    /** <h2>removeAttachementFromCitation</h2>
     * <li>Removes an attachment from the specified {@link AbstractCitation}.</li>
     *
     * @param citationToRemoveAttachment the {@link AbstractCitation} from which to remove the attachment
     */
    public void removeAttachmentFromCitation(AbstractCitation citationToRemoveAttachment) {
        SqlWriter.updateCitationInLibrary(this.activeLibraryTableName, citationToRemoveAttachment);
    }

    /** <h2>updateLibraryWithEditedCitation</h2>
     * <li>Updates the active {@link CitationManager} with the changes made to the selected {@link AbstractCitation}.</li>
     *
     * @param editedCitation the edited {@link AbstractCitation} to update in the library
     */
    public void updateLibraryWithEditedCitation(AbstractCitation editedCitation) {
        SqlWriter.updateCitationInLibrary(this.activeLibraryTableName, editedCitation);

        this.library.refreshLibraryFromDatabase(this.activeLibraryTableName);

        setSelectedCitation(editedCitation);
    }

    /** <h2>addCitationToLibrary</h2>
     * <li>Adds a new {@link AbstractCitation} to the active {@link CitationManager}.</li>
     *
     * @param newCitation the new {@link AbstractCitation} to add to the library
     */
    public void addCitationToLibrary(AbstractCitation newCitation) {
        SqlWriter.addNewCitationToLibraryTable(this.activeLibraryTableName, newCitation);

        this.library.refreshLibraryFromDatabase(this.activeLibraryTableName);
    }


    public void filterCitationsByKeywords(String[] authorAndTitleKeywordsForDatabaseSearch) {
        this.library.filterCitationsByKeywords(this.activeLibraryTableName, authorAndTitleKeywordsForDatabaseSearch);
    }
}
