package de.apaschold.demo.gui;

import de.apaschold.demo.HelloApplication;
import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.logic.filehandling.TextFileHandler;
import de.apaschold.demo.logic.CitationLibrary;
import de.apaschold.demo.model.Citation;
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
import java.util.Optional;

/**
 * <h2>MainViewController</h2>
 * <li>Represents the Main User Interface</li>
 * <li>Offers basic functionalities, e.g. CRUD methods for library, adding and deleting new reference entries</li>
 * <li>Manages the display and interaction with the {@link TableView} of citations
 * and the detailed view of the selected {@link Citation}.</li>
 */

public class MainViewController implements Initializable {
    //0. constants

    //1. attributes

    //2. FXML elements
    @FXML
    private TableView<Citation> citationTable;
    @FXML
    private TableColumn<Citation, String> titleColumn;
    @FXML
    private TableColumn<Citation, String> authorsColumn;
    @FXML
    private TableColumn<Citation, String> journalColumn;
    @FXML
    private TableColumn<Citation, Integer> yearColumn;

    @FXML
    private BorderPane citationView;

    @FXML
    private Label activeLibraryPath;

    //3. constructors/initialize method
    /**
     * <h2>initialize</h2>
     * <li>Sets up {@link TableView}</li>
     * <li>Sets up {@link Citation} view</li>
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
     * <li>Updates the active library path and populates the table with {@link Citation} from the selected {@link CitationLibrary}.</li>
     * <li>Calls {@link Alert} if no valid library was chosen</li>
     */
    @FXML
    protected void openLibrary(){
        Stage stage = (Stage) this.citationView.getScene().getWindow();

        String folderPath = GuiController.getInstance().getActiveLibraryFilePath().replaceAll(AppTexts.REGEX_REPLACE_CML_FILENAME,"");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(folderPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Citation Manager Library",
                "*" + AppTexts.LIBRARY_FILE_FORMAT));
        fileChooser.setTitle("Choose library");
        File chosenFile = fileChooser.showOpenDialog(stage);

        if (chosenFile != null) {
            String chosenLibraryPath = chosenFile.getAbsolutePath();
                GuiController.getInstance().setActiveLibraryFilePath(chosenLibraryPath);
                GuiController.getInstance().fillLibraryFromChosenFile(chosenLibraryPath);

                TextFileHandler.getInstance().saveNewActiveLibraryPath(chosenLibraryPath);

                populateTable();

                this.activeLibraryPath.setText(chosenLibraryPath);
        } else {
            Alerts.showInformationNoFileChoosen();
        }
    }

    /**
     * <h2>saveLibraryToCml</h2>
     * <li>Saves the current state of the active library to a .cml file.</li>
     */
    @FXML
    protected void saveLibraryToCml() {
        GuiController.getInstance().saveActiveLibraryToCml();
    }

    /**
     * <h2>createNewLibrary</h2>
     * <li>Opens {@link de.apaschold.demo.gui.CreateNewLibraryViewController}</li>
     * <li>Creates a new {@link CitationLibrary} and clears the current {@link TableView}.</li>
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
     * <li>Adds a new {@link Citation} to the active {@link CitationLibrary} and refreshes the {@link TableView}.</li>
     */
    @FXML
    protected void addNewCitation() {
        GuiController.getInstance().loadAddNewCitationView();

        // Refresh the table to show the newly added citation
        populateTable();
    }

    /**
     * <h2>deleteSelectedCitation</h2>
     * <li>Deletes the currently selected {@link Citation} from the active  {@link CitationLibrary} after user confirmation.</li>
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
     * <li>Imports {@link Citation} from a BibTeX file and add them to the active {@link CitationLibrary}</li>
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
     * <li>Exports the current active {@link CitationLibrary} to a BibTeX file.</li>
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

    //5. other methods
    /**
     * <h2>populateTable</h2>
     * <li>Populates the {@link TableView} with {@link Citation} from the active {@link CitationLibrary}.</li>
     * <li>Sets up cell value factories for table columns.</li>
     * <li>Adds a selection listener to update the {@link de.apaschold.demo.gui.citationdetailsview.CitationDetailsViewController} when a citation is selected.</li>
     */
    protected void populateTable(){
        List<Citation> citations = GuiController.getInstance().getCitationList();

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

    private ChangeListener<Citation> getSelectionListener(){
        return (observable, oldCitation, newCitation) -> {
            GuiController.getInstance().setSelectedCitation(newCitation);

            populateCitationView();
        };
    }

    /**
     * <h2>populateCitationView</h2>
     * <li>Loads and displays the detailed view of the selected {@link Citation} based on its type.</li>
     */
    public void populateCitationView(){
        if (GuiController.getInstance().getSelectedCitation() != null) {

            try {
                Parent citationDetailsView = FXMLLoader.load(HelloApplication.class.getResource("main-citation-details-view.fxml"));
                this.citationView.setCenter(citationDetailsView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}