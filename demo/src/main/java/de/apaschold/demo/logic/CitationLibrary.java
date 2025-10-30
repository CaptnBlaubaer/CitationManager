package de.apaschold.demo.logic;

import de.apaschold.demo.gui.GuiController;
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
     * @param activeLibraryFilePath path of the active library file as String
     */
    public CitationLibrary(String activeLibraryFilePath) {
        importLibraryFromFile(activeLibraryFilePath);
    }

    //3. getter and setter methods
    public List<Citation> getCitations() {
        return this.citations;
    }

    public Citation getFirstCitation(){ return this.citations.isEmpty() ? null : this.citations.getFirst();}

    public boolean isEmpty(){ return this.citations.isEmpty();}

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
     * <h2>importLibraryFromFile</h2>
     * <li>Imports the citation library from a .cml file, in csv format.</li>
     *
     * @param activeLibraryFilePath path of the active library file as String
     */
    public void importLibraryFromFile(String activeLibraryFilePath){
        this.citations = TextFileHandler.getInstance().importLibraryFromCmlFile(activeLibraryFilePath);
    }


    public void updateSelectedCitation(Citation editedCitation) {
        Citation oldCitation = GuiController.getInstance().getSelectedCitation();

        for(int index = 0; index < this.citations.size(); index++) {
            if(this.citations.get(index).equals(oldCitation)) {
                this.citations.set(index, editedCitation);
                break;
            }
        }
    }
}
