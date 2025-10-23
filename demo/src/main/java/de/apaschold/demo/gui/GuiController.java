package de.apaschold.demo.gui;

import de.apaschold.demo.HelloApplication;
import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.logic.ArticleLibrary;
import de.apaschold.demo.logic.filehandling.TextFileHandler;
import de.apaschold.demo.model.ArticleReference;
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
 */

public class GuiController {
    //0. constants

    //1. attributes
    private static GuiController instance;
    private Stage mainStage;
    private final ArticleLibrary library;
    private ArticleReference selectedArticle;
    private String activeLibraryFilePath;
    private JSONObject referenceChanges;

    //2. constructors

    /**
     * Private constructor for singleton pattern.
     * Loads the last used library file path and initializes the article library.
     * If the library is empty, sets the active library file path to the program directory.
     */
    private GuiController() {
        this.activeLibraryFilePath = TextFileHandler.getInstance().loadLibraryFilePath();

        this.library = new ArticleLibrary( this.activeLibraryFilePath);

        if (this.library.getArticles().isEmpty()){
            setActiveLibraryFilePath(System.getProperty("user.dir"));
        }
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

    public List<ArticleReference> getArticleList() {
        return this.library.getArticles();
    }

    public ArticleLibrary getArticleLibrary() {
        return this.library;
    }

    public ArticleReference getSelectedArticle() { return this.selectedArticle;}

    public void setSelectedArticle(ArticleReference selectedArticle) { this.selectedArticle = selectedArticle;}

    public String getActiveLibraryFilePath() { return this.activeLibraryFilePath;}

    public void setActiveLibraryFilePath(String activeLibraryFilePath) { this.activeLibraryFilePath = activeLibraryFilePath;}

    public JSONObject getReferenceChangesAsJsonObject(){ return this.referenceChanges;}

    public void setReferenceChanges(JSONObject referenceChanges){ this.referenceChanges = referenceChanges;}

    //4. open view methods
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
     * <h2>loadAddNewArticleView</h2>
     * Loads the view for adding a new article reference.
     */
    public void loadAddNewArticleView() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-new-article-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 320, 500);
            Stage newArticleStage = new Stage();
            newArticleStage.setTitle("Add New Article");
            newArticleStage.setScene(scene);
            newArticleStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h2>loadReferenceUpdateView</h2>
     * Loads the view for updating article references from PubMed.
     */
    public void loadReferenceUpdateView() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reference-update-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
            Stage newArticleStage = new Stage();
            newArticleStage.setTitle("Reference update from PubMed");
            newArticleStage.setScene(scene);
            newArticleStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h2>loadImportFromBibTexView</h2>
     * Loads the view for importing article references from a BibTex file.
     */
    public void loadImportFromBibTexView() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("import-from-bibtex-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 320, 250);
            Stage newArticleStage = new Stage();
            newArticleStage.setTitle("Import from BibTex");
            newArticleStage.setScene(scene);
            newArticleStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h2>loadCreateNewLibraryView</h2>
     * Loads the view for creating a new empty article library.
     */
    public void loadCreateNewLibraryView() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-new-library-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 300, 130);
            Stage newArticleStage = new Stage();
            newArticleStage.setTitle("Create empty library");
            newArticleStage.setScene(scene);
            newArticleStage.showAndWait();

            this.library.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //5. other
    /**
     * <h2>exportActiveLibraryToBibTex</h2>
     * Exports the active article library to a BibTex file.
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
     * Saves the current state of the active article library to the CML file.
     */
    public void saveActiveLibraryToCml(){
        TextFileHandler.getInstance().exportLibraryToCml(this.library.getArticles(), this.activeLibraryFilePath);
    }

    /**
     * <h2>fillLibraryFromChosenFile</h2>
     * Imports article references from the specified file into the active article library.
     *
     * @param activeLibraryFilePath the file path of the library file to import from
     */
    public void fillLibraryFromChosenFile(String activeLibraryFilePath){
        this.library.importLibraryFromFile(activeLibraryFilePath);
    }

    /**
     * <h2>deleteSelectedArticle</h2>
     * Deletes the currently selected article reference from the active article library.
     */
    public void deleteSelectedArticle() {
        this.library.deleteArticle(this.selectedArticle);
    }
}
