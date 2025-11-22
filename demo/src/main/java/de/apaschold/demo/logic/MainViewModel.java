package de.apaschold.demo.logic;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.gui.Alerts;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.logic.databasehandling.SqlWriter;
import de.apaschold.demo.logic.filehandling.FileHandler;
import de.apaschold.demo.logic.filehandling.TextFileHandler;
import de.apaschold.demo.logic.filehandling.WebHandler;
import de.apaschold.demo.model.AbstractCitation;
import de.apaschold.demo.model.CitationViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class MainViewModel {
    //0. constants

    //1. attributes
    private static MainViewModel instance;

    private CitationService citationService;
    private AbstractCitation selectedCitation;
    private AbstractCitation dummyCitationToEdit;
    private JSONObject citationUpdatesFromPubMed;

    private final ObservableList<CitationViewModel> citations = FXCollections.observableArrayList();
    private final ObjectProperty<CitationViewModel> selected = new SimpleObjectProperty<>();

    //2. constructor and getInstance method
    private MainViewModel () {
        this.citationService = new CitationService();
        //loadCitations()
        this.selectedCitation = this.citationService.findAllCitations().getFirst();
    }

    public static synchronized MainViewModel getInstance(){
        if(instance == null){
            instance = new MainViewModel();
        }
        return instance;
    }

    //3. getter and setter methods
    public void setSelectedCitation(AbstractCitation citation) { this.selectedCitation = citation;}
    public AbstractCitation getSelectedCitation() { return selectedCitation;}

    public void setDummyCitation(AbstractCitation dummyCitation){ this.dummyCitationToEdit = dummyCitation;}
    public AbstractCitation getDummyCitation(){ return this.dummyCitationToEdit;}

    public JSONObject getCitationUpdatesFromPubMed(){ return this.citationUpdatesFromPubMed;}

    //4. CRUD operations on Database via CitationService
    public List<AbstractCitation> loadCitations() { return this.citationService.findAllCitations();}

    public List<AbstractCitation> loadFilteredCitations(String authorKeyword, String titleKeyword) {
        return this.citationService.filteredCitationsByKeywords(authorKeyword, titleKeyword);
    }

    /**
     * <h2>deleteSelectedCitation</h2>
     * Deletes the currently selected {@link AbstractCitation} from the active {@link CitationManager}.
     */
    public void deleteSelectedCitation() {
        this.citationService.deleteCitation(this.selectedCitation);
    }

    /** <h2>add new citation</h2>
     * <li>Collects data from the citation form and creates a new {@link AbstractCitation}e.</li>
     * <li>Adds the new {@link AbstractCitation} to the {@link CitationManager} and closes the add new citation view.</li>
     */
    public void addNewCitation(String citationAsString) {
        AbstractCitation newCitation = CitationFactory.createCitationFromManualDataInput(citationAsString);

        this.citationService.addCitation(newCitation);
    }

    public void updateCitationInDatabase(AbstractCitation citation) {
        this.citationService.updateCitationInDatabase(citation);
    }


    //5. other methods
    /** <h2>createNewLibrary</h2>
     * <li>Creates a new {@link CitationManager} file and a folder for the pdfs with the specified name in the selected folder path.</li>
     * <li>Saves the active library file path in a .txt file.</li>
     * <li>Initializes a new {@link CitationService} for the new library.</li>
     * <li>Creates a new database and a new table for the citations.</li>
     */
    public void createNewLibrary(String filePath){
        File newLibraryFile = new File(filePath);

        if (!newLibraryFile.exists()) {
            FileHandler.getInstance().createEmptyLibrary(newLibraryFile);

            TextFileHandler.getInstance().saveNewActiveLibraryPath(filePath);

            this.citationService = new CitationService();

            SqlWriter.createNewLibraryDatabase();
            SqlWriter.createNewLibraryTable(AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS);
        } else {
            Alerts.showAlertFileNameAlreadyExists();
        }
    }

    /**
     * <h2>openLibraryFile</h2>
     * <li>Opens new {@link CitationManager} file from selected Path.</li>
     *
     * @param chosenLibraryPath the file path of the library file to import from
     */
    public void openLibrary(String chosenLibraryPath) {
        TextFileHandler.getInstance().saveNewActiveLibraryPath(chosenLibraryPath);

        this.citationService = new CitationService();
    }

    /** <h2>importBibTex</h2>
     * <li>Parses the BibTex formatted text and adds the corresponding {@link AbstractCitation} to the {@link CitationManager}.</li>
     * <li>Creates {@link AbstractCitation} through {@link CitationFactory}</li>
     * @param bibTexText The BibTex formatted text input.
     */
    public void importBibTex (String bibTexText){
        String[] separatedImports = bibTexText.replace("@", "!!!!!@").split("!!!!!");

        for (String singleImport : separatedImports) {
            if (!singleImport.isEmpty()) {
                AbstractCitation importedCitation = CitationFactory.createCitationFromBibTex(singleImport);
                this.citationService.addCitation(importedCitation);
            }
        }
    }

    /**
     * <h2>exportActiveLibraryToBibTex</h2>
     * Exports the active {@link CitationManager} to a BibTex file.
     * The BibTex file is created in the same directory as the active library file,
     * with the same name but with a .bib extension.
     *
     * @throws NullPointerException if the library is empty and there is nothing to export
     */
    public void exportActiveLibraryToBibTex() {
        String bibTexString = this.citationService.citationsAsBibTexString();

        if(!bibTexString.isEmpty()) {
            String bibTexFilePath = CitationService.getActiveLibraryFilePath().replace(AppTexts.LIBRARY_FILE_FORMAT, AppTexts.BIBTEX_FILE_FORMAT);

            TextFileHandler.getInstance().exportLibraryToBibTex(bibTexString.toString(), bibTexFilePath);
        } else {
            throw new NullPointerException();
        }
    }

    /** <h2>createDummyCitationToEdit</h2>
     * <li>Creates a dummy {@link AbstractCitation} to edit by converting the selected citation to a CSV string
     * and then creating a new citation from that string.</li>
     * <li>Sets the dummy citation as the citation to edit.</li>
     * <li>Dummy citation is necessary to not change the original data before saving</li>
     */
    public void createDummyCitationToEdit(){
        String csvRepresentation = this.selectedCitation.toCsvString();

        this.dummyCitationToEdit = CitationFactory.createCitationFromCsvLine(csvRepresentation);
    }

    public void checkForCitationUpdates(String pubMedString){
        //returned String is %journal_title|year|||author_last_name|Art1|pub_med_id
        String pubMedId = WebHandler.getInstance().getPubMedId(pubMedString).split("Art1\\|")[1];

        if(!pubMedId.contains("NOT_FOUND")) {
            citationUpdatesFromPubMed = WebHandler.getInstance().getRecordsFromPubMedId(pubMedId);

            GuiController.getInstance().loadReferenceUpdateView();

            GuiController.getInstance().loadMainMenu();
        } else {
            Alerts.showInformationRecordNotFound();
        }
    }


}
