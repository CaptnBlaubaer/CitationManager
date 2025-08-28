package de.apaschold.demo.model;

import de.apaschold.demo.additionals.AppTexts;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class ArticleReference {
    //0. constants

    //1. attributes
    protected ArticleType articleType;
    protected String title;
    protected String author;
    protected String journal;
    protected int year;
    protected String doi;
    protected String pdfFilePath;

    //2. constructors
    public ArticleReference() {
        this.title = AppTexts.PLACEHOLDER;
        this.author = AppTexts.PLACEHOLDER;
        this.journal = AppTexts.PLACEHOLDER;
        this.year = AppTexts.NUMBER_PLACEHOLDER;
        this.doi = AppTexts.PLACEHOLDER;
        this.pdfFilePath = AppTexts.PLACEHOLDER;
    }

    public ArticleReference(ArticleType type, String title, String author, String journal, int year, String doi, String pdfFilePath) {
        this.articleType = type;
        this.title = title;
        this.author = author;
        this.journal = journal;
        this.year = year;
        this.doi = doi;
        this.pdfFilePath = pdfFilePath;
    }

    //3. getter and setter methods
    public ArticleType getArticleType() { return articleType;}

    public void setArticleType(ArticleType articleType) { this.articleType = articleType;}

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title;}

    public String getAuthor() { return author;}

    public void setAuthor(String author) { this.author = author;}

    public String getJournal() { return journal;}

    public void setJournal(String journal) { this.journal = journal;}

    public int getYear() { return year;}

    public void setYear(int year) { this.year = year;}

    public String getDoi() { return doi;}

    public void setDoi(String doi) { this.doi = doi;}

    public String getPdfFilePath() { return pdfFilePath;}

    public void setPdfFilePath(String pdfFilePath) { this.pdfFilePath = pdfFilePath;}

    //Property getters for TableView
    public StringProperty titleProperty() { return new SimpleStringProperty(title);}

    public StringProperty authorsProperty() { return new SimpleStringProperty(author);}

    public StringProperty journalProperty() { return new SimpleStringProperty(journal);}

    public IntegerProperty yearProperty() { return new SimpleIntegerProperty(year);}

    //4. other methods
    public String toCsvString(){ return "";}

    public String exportAsBibTexString(){ return "";}

    public String createBibTexReference(){
        String firstAuthor = this.author.split(" and ")[0];
        String firstAuthorLastName = firstAuthor.split(", ")[0];
        char firstAuthorPreNameFirstCharacter = firstAuthor.split(", ")[1].charAt(0);
        String publishedYear = this.year + "";
        String journalWithoutWhitespace = this.journal.replaceAll(" ","").replaceAll("\\.","");

        return firstAuthorLastName + firstAuthorPreNameFirstCharacter + publishedYear + journalWithoutWhitespace;
    }
}
