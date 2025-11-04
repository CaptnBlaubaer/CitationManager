package de.apaschold.demo.logic;

import de.apaschold.demo.logic.databasehandling.SqlReader;
import de.apaschold.demo.logic.filehandling.TextFileHandler;
import de.apaschold.demo.model.Citation;

import java.util.List;

public class CitationLibrary {
    //0. constants

    //1. attributes
    private List<Citation> citations;

    //2. constructors
    /**
     * <h2>CitationLibrary</h2>
     * <li>Initializes the citation library by calling import method for last used library</li>
     *
     * @param activeLibraryName name of the active library
     */
    public CitationLibrary(String activeLibraryName) {
        refreshLibraryFromDatabase(activeLibraryName);
    }

    //3. getter and setter methods
    public List<Citation> getCitations() {
        return this.citations;
    }

    public Citation getFirstCitation(){ return this.citations.isEmpty() ? null : this.citations.getFirst();}

    //4. methods to modify list
    public void clear(){ this.citations.clear();}

    public void addCitation(Citation citation){
        this.citations.add(citation);
    }

    public void deleteCitation(Citation citation){ this.citations.remove(citation);}

    //5. export/import methods

    /**
     * <h2>generateStringForBibTex</h2>
     * <li>Generates a BibTex representation of the citation library as a String.</li>
     *
     * @return the citation library as BibTex String
     */
    public String generateStringForBibTex (){
        StringBuilder libraryAsBibTex = new StringBuilder();

        for(Citation reference : this.citations) {
            libraryAsBibTex.append(reference.exportAsBibTexString()).append("\n\n");
        }

        return libraryAsBibTex.toString();
    }

    /**
     * <h2>refreshLibraryFromDatabase</h2>
     * <li>Refreshes the citation library by re-importing citations from the database.</li>
     *
     * @param activeLibraryName name of the active library
     */
    public void refreshLibraryFromDatabase(String activeLibraryName) {
        this.citations = SqlReader.importCitationsFromLibraryTableSqlite(activeLibraryName);
    }

    /**
     * <h2>importLibraryFromFile</h2>
     * <li>Imports citations from a specified library file.</li>
     *
     * @param chosenLibraryFilePath path to the chosen library file
     */
    public void importLibraryFromFile(String chosenLibraryFilePath) {
        this.citations = TextFileHandler.getInstance().importLibraryFromCmlFile(chosenLibraryFilePath);
    }
}
