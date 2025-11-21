package de.apaschold.demo.gui;

import de.apaschold.demo.HelloApplication;
import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.logic.filehandling.TextFileHandler;
import de.apaschold.demo.logic.CitationManager;
import de.apaschold.demo.model.AbstractCitation;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <h2>MainViewController</h2>
 * <li>Represents the Main User Interface</li>
 * <li>Offers basic functionalities, e.g. CRUD methods for library, adding and deleting new reference entries</li>
 * <li>Manages the display and interaction with the {@link TableView} of citations
 * and the detailed view of the selected {@link AbstractCitation}.</li>
 */

public class MainViewController implements Initializable {
    //0. constants

    //1. attributes

    //2. FXML elements
    @FXML
    private TextField authorFilter;
    @FXML
    private TextField keywordFilter;

    @FXML
    private TableView<AbstractCitation> citationTable;
    @FXML
    private TableColumn<AbstractCitation, String> titleColumn;
    @FXML
    private TableColumn<AbstractCitation, String> authorsColumn;
    @FXML
    private TableColumn<AbstractCitation, String> journalColumn;
    @FXML
    private TableColumn<AbstractCitation, Integer> yearColumn;

    @FXML
    private BorderPane citationView;

    @FXML
    private Label activeLibraryPath;

    //3. constructors/initialize method
    /**
     * <h2>initialize</h2>
     * <li>Sets up {@link TableView}</li>
     * <li>Sets up {@link AbstractCitation} view</li>
     * <li>Displays the active library file path in the upper </li>
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        populateTable();

        if(GuiController.getInstance().getSelectedCitation() != null){
            populateCitationView();
        }

        this.activeLibraryPath.setText(GuiController.getInstance().getActiveLibraryFilePath());
    }

    //4. FXML methods
    /**
     * <h2>openLibrary</h2>
     * <li>Opens a file chooser dialog to select a new library file.</li>
     * <li>Updates the active library path and populates the table with {@link AbstractCitation} from the selected {@link CitationManager}.</li>
     * <li>Calls {@link Alert} if no valid library was chosen</li>
     */
    @FXML
    protected void openLibrary(){
        Stage stage = (Stage) this.citationView.getScene().getWindow();

        String folderPath = GuiController.getInstance().getActiveLibraryFilePath().replaceAll(AppTexts.REGEX_REPLACE_DB_FILENAME,"");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(folderPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQLite Database",
                "*" + AppTexts.LIBRARY_FILE_FORMAT));
        fileChooser.setTitle("Choose library");
        File chosenFile = fileChooser.showOpenDialog(stage);

        if (chosenFile != null) {
            String chosenLibraryPath = chosenFile.getAbsolutePath();

                GuiController.getInstance().openLibraryFile(chosenLibraryPath);

                TextFileHandler.getInstance().saveNewActiveLibraryPath(chosenLibraryPath);

                populateTable();

                this.activeLibraryPath.setText(chosenLibraryPath);
        } else {
            Alerts.showInformationNoFileChosen();
        }
    }

    /**
     * <h2>createNewLibrary</h2>
     * <li>Opens {@link de.apaschold.demo.gui.CreateNewLibraryViewController}</li>
     * <li>Creates a new {@link CitationManager} and clears the current {@link TableView}.</li>
     * <li>Updates the active library path display.</li>
     */
    @FXML
    protected void createNewLibrary(){
        GuiController.getInstance().loadCreateNewLibraryView();

        this.citationTable.getItems().clear();

        this.activeLibraryPath.setText(GuiController.getInstance().getActiveLibraryFilePath());
    }

    /**
     * <h2>addNewCitation</h2>
     * <li>Opens {@link AddNewCitationViewController}</li>
     * <li>Adds a new {@link AbstractCitation} to the active {@link CitationManager} and refreshes the {@link TableView}.</li>
     */
    @FXML
    protected void addNewCitation() {
        GuiController.getInstance().loadAddNewCitationView();

        // Refresh the table to show the newly added citation
        populateTable();
    }

    /**
     * <h2>deleteSelectedCitation</h2>
     * <li>Deletes the currently selected {@link AbstractCitation} from the active  {@link CitationManager} after user confirmation.</li>
     * <li>Refreshes the {@link TableView} to reflect the deletion.</li>
     */
    @FXML
    protected void deleteSelectedCitation() {
        Optional<ButtonType> confirmDeletion = Alerts.showConfirmationDeleteCitation();

        if (confirmDeletion.get() == ButtonType.OK){
            GuiController.getInstance().deleteSelectedCitation();

            populateTable();
        }
    }

    /**
     * <h2>importFromBibTex</h2>
     * <li>Opens {@link de.apaschold.demo.gui.ImportFromBibTexViewController}</li>
     * <li>Imports {@link AbstractCitation} from a BibTeX file and add them to the active {@link CitationManager}</li>
     * <li>Refreshes the {@link TableView}.</li>
     */
    @FXML
    protected void importFromBibTex(){
        GuiController.getInstance().loadImportFromBibTexView();

        // Refresh the table to show the newly imported citation(s)
        populateTable();
    }

    /**
     * <h2>exportToBibTex</h2>
     * <li>Exports the current active {@link CitationManager} to a BibTeX file.</li>
     * <li>Shows an alert if the library is empty.</li>
     */
    @FXML
    protected void exportToBibTex(){
        try {
            GuiController.getInstance().exportActiveLibraryToBibTex();
        } catch (NullPointerException e) {
            Alerts.showAlertMessageEmptyLibrary();
        }
    }

    @FXML
    protected void filterCitations(){
        String authorKeyword = this.authorFilter.getText().trim();
        String titleKeyword = this.keywordFilter.getText().trim();

        String[] authorAndTitleKeywordsForDatabaseSearch = {authorKeyword, titleKeyword};
        if (!authorKeyword.isEmpty() || !titleKeyword.isEmpty()) {
            GuiController.getInstance().filterCitationsByKeywords(authorAndTitleKeywordsForDatabaseSearch);
            populateTable();
        } else {
            Alerts.showNoFilterKeyWordsChosen();
        }
    }

    //5. other methods
    /**
     * <h2>populateTable</h2>
     * <li>Populates the {@link TableView} with {@link AbstractCitation} from the active {@link CitationManager}.</li>
     * <li>Sets up cell value factories for table columns.</li>
     * <li>Adds a selection listener to update the {@link de.apaschold.demo.gui.citationdetailsview.CitationDetailsViewController} when a citation is selected.</li>
     */
    protected void populateTable(){
        List<AbstractCitation> citations = GuiController.getInstance().getCitationList();

        this.citationTable.getItems().clear();

        if (!citations.isEmpty()){
            this.citationTable.getItems().addAll(citations);

            this.titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
            this.authorsColumn.setCellValueFactory(cellData -> cellData.getValue().authorsProperty());
            this.journalColumn.setCellValueFactory(cellData -> cellData.getValue().journalProperty());
            this.yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        } else {
            this.citationTable.setPlaceholder(new Label( AppTexts.TABLE_VIEW_PLACEHOLDER));
        }

        this.citationTable.getSelectionModel().selectedItemProperty().addListener(getSelectionListener());
    }

    private ChangeListener<AbstractCitation> getSelectionListener(){
        return (observable, oldCitation, newCitation) -> {
            GuiController.getInstance().setSelectedCitation(newCitation);

            populateCitationView();
        };
    }

    /**
     * <h2>populateCitationView</h2>
     * <li>Loads and displays the detailed view of the selected {@link AbstractCitation} based on its type.</li>
     */
    public void populateCitationView(){
        if (GuiController.getInstance().getSelectedCitation() != null) {

            try {
                Parent citationDetailsView = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("main-citation-details-view.fxml")));
                this.citationView.setCenter(citationDetailsView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}