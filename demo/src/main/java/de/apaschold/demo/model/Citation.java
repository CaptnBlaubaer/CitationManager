package de.apaschold.demo.model;

import de.apaschold.demo.additionals.AppTexts;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Arrays;

/**
 * <h2>Citation</h2>
 * <li>Abstract superclass for different types of citations (e.g., JournalArticle, Book).</li>
 * <li>Holds common attributes and methods</li>
 */

public abstract class Citation {
    //0. constants

    //1. attributes
    protected CitationType citationType;
    protected String title;
    protected String author;
    protected String journal;
    protected int year;
    protected String doi;
    protected String[] pdfFilePaths;

    //2. constructors
    public Citation() {
        this.title = AppTexts.PLACEHOLDER;
        this.author = AppTexts.PLACEHOLDER;
        this.journal = AppTexts.PLACEHOLDER;
        this.year = AppTexts.NUMBER_PLACEHOLDER;
        this.doi = AppTexts.PLACEHOLDER;
        this.pdfFilePaths = AppTexts.PLACEHOLDER.split(",");
    }

    public Citation(CitationType type, String title, String author, String journal, int year, String doi, String pdfFilePaths) {
        this.citationType = type;
        this.title = title;
        this.author = author;
        this.journal = journal;
        this.year = year;
        this.doi = doi;
        this.pdfFilePaths = pdfFilePaths.split(",");
    }

    //3. getter and setter methods
    public CitationType getCitationType() { return citationType;}

    public void setCitationType(CitationType citationType) { this.citationType = citationType;}

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title;}

    public String getAuthor() { return author;}

    public void setAuthors(String author) { this.author = author;}

    public String getJournal() { return journal;}

    public void setJournal(String journal) { this.journal = journal;}

    public int getYear() { return year;}

    public void setYear(int year) { this.year = year;}

    public String getDoi() { return doi;}

    public void setDoi(String doi) {
        if(doi.contains("https://doi.org/")){
            doi = doi.replace("https://doi.org/", "");
        }
        this.doi = doi;
    }

    public String[] getPdfFilePaths() { return pdfFilePaths;}

    public void setPdfFilePaths(String[] pdfFilePaths) { this.pdfFilePaths = pdfFilePaths;}

    //Property getters for TableView
    public StringProperty titleProperty() { return new SimpleStringProperty(title);}

    public StringProperty authorsProperty() { return new SimpleStringProperty(author);}

    public StringProperty journalProperty() { return new SimpleStringProperty(journal);}

    public IntegerProperty yearProperty() { return new SimpleIntegerProperty(year);}

    //4. other methods
    public String toCsvString(){ return "";}

    public String citationDetailsAsString(){ return "";}

    public String exportAsBibTexString(){ return "";}

    public String createPubMedSearchTerm(){ return "";}

    /**
     * <h2>createBibTexReference</h2>
     * <li>generates reference code for BibTex-format</li>
     *
     * @return bibTex reference as "LastName + FirstNameFirstCharacter + Year + Journal/Publisher"
     */
    public String createBibTexReference(){
        String firstAuthor = this.author.split(" and ")[0];
        //author names are in the Format "LastName, Prename"
        //replaces white spaces in case of two or more LastNames (e.g. spanish authors)
        String firstAuthorLastName = firstAuthor.split(", ")[0].replaceAll(" ","");
        char firstAuthorPreNameFirstCharacter = firstAuthor.split(", ")[1].charAt(0);
        String publishedYear = this.year + "";
        String journalWithoutWhitespace = this.journal.replaceAll(" ","").replaceAll("\\.","");

        return firstAuthorLastName + firstAuthorPreNameFirstCharacter + publishedYear + journalWithoutWhitespace;
    }

    /**
     * <h2>addNewAttachment</h2>
     * <li>Adds a new attachment file path to the existing list of PDF file paths.</li>
     *
     * @param newAttachement the file path of the new attachment to be added
     */
    public void addNewAttachment(String newAttachement){
        System.out.println(Arrays.toString(this.pdfFilePaths));
        String oldAttachmentsAsString = String.join(",", this.pdfFilePaths);

        System.out.println(Arrays.toString(this.pdfFilePaths));
        if (oldAttachmentsAsString.equals(AppTexts.PLACEHOLDER)){
            this.pdfFilePaths = newAttachement.split(",");
        }
        else {
            oldAttachmentsAsString = oldAttachmentsAsString + "," + newAttachement;
            this.pdfFilePaths = oldAttachmentsAsString.split(",");
        }

        System.out.println(Arrays.toString(this.pdfFilePaths));
    }

    public void removeAttachment(String chosenAttachment){
        String attachmentNamesAsString = String.join(",", this.pdfFilePaths);
        attachmentNamesAsString = attachmentNamesAsString.replace(chosenAttachment,"");

        String[] attachmentArray = attachmentNamesAsString.split(",");

        if (attachmentArray[0].isEmpty()){
            this.pdfFilePaths = AppTexts.PLACEHOLDER.split(",");
        } else {
            this.pdfFilePaths = attachmentArray;
        }
    }
}
