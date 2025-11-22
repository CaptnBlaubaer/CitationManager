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

    public static void setActiveLibraryFilePath(String newFilePath) { activeLibraryFilePath = newFilePath;}

    //4. CRUD methods
    public List<AbstractCitation> findAllCitations(){ return SqlReader.importCitationsFromLibraryTable(this.activeTableName);}

    public List<AbstractCitation> filteredCitationsByKeywords(String authorKeyWord, String titleKeyword){
        return SqlReader.filterCitationsByKeywords(this.activeTableName, authorKeyWord, titleKeyword);
    }

    public void addCitation(AbstractCitation newCitation) {
        SqlWriter.addNewCitationToLibraryTable(this.activeTableName, newCitation);
    }

    public void updateCitationInDatabase(AbstractCitation citationToUpdate) {
        SqlWriter.updateCitationInLibrary(this.activeTableName, citationToUpdate);
    }

    public void deleteCitation(AbstractCitation selectedCitation) {
        SqlWriter.deleteCitationFromLibrary(this.activeTableName, selectedCitation);
    }

    //5. methods
    public String citationsAsBibTexString() {
        List<AbstractCitation> citations = findAllCitations();

        StringBuilder bibTexString = new StringBuilder();

        for(AbstractCitation citation : citations) {
            bibTexString.append(citation.exportAsBibTexString()).append("\n\n");
        }

        return bibTexString.toString();
    }
}
