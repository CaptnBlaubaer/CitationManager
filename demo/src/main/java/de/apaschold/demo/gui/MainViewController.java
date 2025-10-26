package de.apaschold.demo.gui;

import de.apaschold.demo.HelloApplication;
import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.logic.filehandling.TextFileHandler;
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
 * <li>Manages the display and interaction with the list of article references
 * and the detailed view of the selected article.</li>
 */

public class MainViewController implements Initializable {
    //0. constants

    //1. attributes

    //2. FXML elements
    @FXML
    private TableView<Citation> articlesTable;
    @FXML
    private TableColumn<Citation, String> titleColumn;
    @FXML
    private TableColumn<Citation, String> authorsColumn;
    @FXML
    private TableColumn<Citation, String> journalColumn;
    @FXML
    private TableColumn<Citation, Integer> yearColumn;

    @FXML
    private BorderPane articleView;

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

        if(GuiController.getInstance().getSelectedArticle() != null){
            populateArticleView();
        }

        this.activeLibraryPath.setText(GuiController.getInstance().getActiveLibraryFilePath());
    }

    //4. FXML methods
    /**
     * <h2>openLibrary</h2>
     * <li>Opens a file chooser dialog to select a new library file.</li>
     * <li>Updates the active library path and populates the table with articles from the selected library.</li>
     * <li>Calls {@link Alert} if no valid library was chosen</li>
     */
    @FXML
    protected void openLibrary(){
        Stage stage = (Stage) this.articleView.getScene().getWindow();

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
     * <li>Creates a new library and clears the current article list.</li>
     * <li>Updates the active library path display.</li>
     */
    @FXML
    protected void createNewLibrary(){
        GuiController.getInstance().loadCreateNewLibraryView();

        this.articlesTable.getItems().clear();

        this.activeLibraryPath.setText(GuiController.getInstance().getActiveLibraryFilePath());
    }

    /**
     * <h2>addNewArticle</h2>
     * <li>Opens {@link AddNewArticleViewController}</li>
     * <li>Adds a new article reference to the active library and refreshes the table view.</li>
     */
    @FXML
    protected void addNewArticle() {
        GuiController.getInstance().loadAddNewArticleView();

        // Refresh the table to show the newly added article
        populateTable();
    }

    /**
     * <h2>deleteSelectedArticle</h2>
     * <li>Deletes the currently selected article from the active library after user confirmation.</li>
     * <li>Refreshes the table view to reflect the deletion.</li>
     */
    @FXML
    protected void deleteSelectedArticle() {
        Optional<ButtonType> confirmDeletion = Alerts.showConfirmationDeleteArticle();

        if (confirmDeletion.get() == ButtonType.OK){
            GuiController.getInstance().deleteSelectedArticle();

            populateTable();
        }
    }

    /**
     * <h2>importFromBibTex</h2>
     * <li>Opens {@link de.apaschold.demo.gui.ImportFromBibTexViewController}</li>
     * <li>Imports articles from a BibTeX file and add them to the active library</li>
     * <li>Refreshes the table view.</li>
     */
    @FXML
    protected void importFromBibTex(){
        GuiController.getInstance().loadImportFromBibTexView();

        // Refresh the table to show the newly imported article
        populateTable();
    }

    /**
     * <h2>exportToBibTex</h2>
     * <li>Exports the current active library to a BibTeX file.</li>
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
     * <li>Populates the {@link TableView} with articles from the active library.</li>
     * <li>Sets up cell value factories for table columns.</li>
     * <li>Adds a selection listener to update the article view when an article is selected.</li>
     */
    protected void populateTable(){
        List<Citation> articles = GuiController.getInstance().getArticleList();

        this.articlesTable.getItems().clear();

        if (!articles.isEmpty()){
            this.articlesTable.getItems().addAll(articles);

            this.titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
            this.authorsColumn.setCellValueFactory(cellData -> cellData.getValue().authorsProperty());
            this.journalColumn.setCellValueFactory(cellData -> cellData.getValue().journalProperty());
            this.yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        } else {
            this.articlesTable.setPlaceholder(new Label( AppTexts.TABLE_VIEW_PLACEHOLDER));
        }

        this.articlesTable.getSelectionModel().selectedItemProperty().addListener(getSelectionListener());
    }

    private ChangeListener<Citation> getSelectionListener(){
        return (observable, oldArticle, newArticle) -> {
            GuiController.getInstance().setSelectedArticle(newArticle);

            populateArticleView();
        };
    }

    /**
     * <h2>populateArticleView</h2>
     * <li>Loads and displays the detailed view of the selected article based on its type.</li>
     * <li>Dynamically loads the appropriate FXML subview for the article type.</li>
     */
    public void populateArticleView(){
        //Citation selectedArticle = GuiController.getInstance().getSelectedArticle();

        if (GuiController.getInstance().getSelectedArticle() != null) {
            /*String fxmlFile = switch (selectedArticle.getArticleType()) {
                case JOURNAL_ARTICLE -> "journal-article-subview.fxml";
                case BOOK_SECTION -> "book-section-subview.fxml";
                case BOOK -> "book-subview.fxml";
                case THESIS -> "phd-thesis-subview.fxml";
                case PATENT -> "patent-subview.fxml";
                case UNPUBLISHED -> "unpublished-subview.fxml";
                default -> "";
            };*/

            //if (!fxmlFile.isEmpty()) {
                try {
                    Parent articleDetailsView = FXMLLoader.load(HelloApplication.class.getResource("main-citation-details-view.fxml"));
                    this.articleView.setCenter(articleDetailsView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            //}
        }
    }
}