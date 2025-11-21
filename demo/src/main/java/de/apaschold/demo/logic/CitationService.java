package de.apaschold.demo.logic;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.logic.databasehandling.SqlReader;
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
    public List<AbstractCitation> findAll(){ return SqlReader.importCitationsFromLibraryTable(this.activeTableName);}




    //5. methods

}
