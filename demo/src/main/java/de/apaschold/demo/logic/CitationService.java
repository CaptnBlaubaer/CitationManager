package de.apaschold.demo.logic;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.logic.databasehandling.SqlReader;
import de.apaschold.demo.logic.databasehandling.SqlWriter;
import de.apaschold.demo.logic.filehandling.TextFileHandler;
import de.apaschold.demo.model.AbstractCitation;

import java.util.List;

public class CitationService {
    //0. constants

    //1. attributes
    private static String activeLibraryFilePath;
    private String activeTableName;
    private List<AbstractCitation> citations;


    //2. constructors
    public CitationService() {
        activeLibraryFilePath = TextFileHandler.getInstance().loadLibraryFilePath();;
        this.activeTableName = AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS;
    }

    //3. getter and setter methods
    public static String getActiveLibraryFilePath() { return activeLibraryFilePath;}

    //4. CRUD methods
    public List<AbstractCitation> findAllCitations(){ return SqlReader.importCitationsFromLibraryTable(this.activeTableName);}

    public void addCitation(AbstractCitation newCitation) {
        SqlWriter.addNewCitationToLibraryTable(this.activeTableName, newCitation);
    }

    public void deleteCitation(AbstractCitation selectedCitation) {
        SqlWriter.deleteCitationFromLibrary(this.activeTableName, selectedCitation);
    }


    //5. methods
    /**
     * <h2>changeActiveLibrary</h2>
     * <li>Changes the active {@link CitationManager} to the specified file.</li>
     * <li>Creates a new library database if it does not exist.</
     *
     * @param filePath the file path of the new library file
     */
    public void createNewLibrary(String filePath){
        activeLibraryFilePath = filePath;
        this.activeTableName = AppTexts.SQLITE_TABLE_NAME_ALL_CITATIONS;

        SqlWriter.createNewLibraryDatabase();
        SqlWriter.createNewLibraryTable(this.activeTableName);
    }
}
