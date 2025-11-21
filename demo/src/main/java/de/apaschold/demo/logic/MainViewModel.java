package de.apaschold.demo.logic;

import de.apaschold.demo.gui.Alerts;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.logic.filehandling.FileHandler;
import de.apaschold.demo.logic.filehandling.TextFileHandler;
import de.apaschold.demo.model.AbstractCitation;
import de.apaschold.demo.model.CitationViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;

public class MainViewModel {
    //0. constants

    //1. attributes
    private static MainViewModel instance;

    private final CitationService citationService;
    private AbstractCitation selectedCitation;

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
    public List<AbstractCitation> loadCitations() { return this.citationService.findAllCitations();}

    public void setSelectedCitation(AbstractCitation citation) { this.selectedCitation = citation;}
    public AbstractCitation getSelectedCitation() { return selectedCitation;}

    //4. other methods
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

    /** <h2>createNewLibrary</h2>
     * <li>Creates a new {@link CitationManager} file and a folder for the pdfs with the specified name in the selected folder path.</li>
     * <li>Updates the active library file path in the GuiController and saves it to a .txt file.</li>
     * <li>Closes the create new library view.</li>
     */
    public void createNewLibrary(String filePath){
        File newLibraryFile = new File(filePath);

        if (!newLibraryFile.exists()) {
            FileHandler.getInstance().createEmptyLibrary(newLibraryFile);

            TextFileHandler.getInstance().saveNewActiveLibraryPath(filePath);

            this.citationService.createNewLibrary(filePath);
        } else {
            Alerts.showAlertFileNameAlreadyExists();
        }
    }
}
