package de.apaschold.demo.logic;

import de.apaschold.demo.logic.databasehandling.SqlReader;
import de.apaschold.demo.model.AbstractCitation;

import java.util.List;

public class CitationManager {
    //0. constants

    //1. attributes
    private List<AbstractCitation> citations;

    //2. constructors
    /**
     * <h2>CitationLibrary</h2>
     * <li>Initializes the citation library by calling import method for last used library</li>
     *
     * @param activeLibraryName name of the active library
     */
    public CitationManager(String activeLibraryName) {
        refreshLibraryFromDatabase(activeLibraryName);
    }

    //3. getter and setter methods
    public List<AbstractCitation> getCitations() {
        return this.citations;
    }

    public AbstractCitation getFirstCitation(){ return this.citations.isEmpty() ? null : this.citations.getFirst();}

    //4. methods to modify list
    public void clear(){ this.citations.clear();}

    public void addCitation(AbstractCitation citation){
        this.citations.add(citation);
    }

    public void deleteCitation(AbstractCitation citation){ this.citations.remove(citation);}

    //5. export/import methods
    /**
     * <h2>refreshLibraryFromDatabase</h2>
     * <li>Refreshes the citation library by re-importing citations from the database.</li>
     *
     * @param activeLibraryName name of the active library
     */
    public void refreshLibraryFromDatabase(String activeLibraryName) {
        this.citations = SqlReader.importCitationsFromLibraryTable(activeLibraryName);
    }

    public void filterCitationsByKeywords(String tableName, String[] authorAndTitleKeywordsForDatabaseSearch) {
        this.citations = SqlReader.filterCitationsByKeywords(tableName, authorAndTitleKeywordsForDatabaseSearch);
    }
}
