package de.apaschold.demo.model;

import de.apaschold.demo.additionals.AppTexts;

/**
 * <h2>JournalArticle</h2>
 * <li>Represents a journal article citation, extending the {@link Citation} class.</li>
 */

public class JournalArticle extends Citation {
    //0. constants

    //1. attributes
    private String journalShortForm;
    private int volume;
    private int issue;
    private String pages;

    //2. constructors
    public JournalArticle(){
        super.setCitationType(CitationType.JOURNAL_ARTICLE);
        this.journalShortForm = AppTexts.PLACEHOLDER;
        this.volume = AppTexts.NUMBER_PLACEHOLDER;
        this.issue = AppTexts.NUMBER_PLACEHOLDER;
        this.pages = AppTexts.PLACEHOLDER;
    }

    public JournalArticle(String title, String author, String journal, String journalShortForm, int year, int volume, int issue, String pages, String doi, String pdfFilePath) {
        super(CitationType.JOURNAL_ARTICLE, title, author, journal, year, doi, pdfFilePath);
        this.journalShortForm = journalShortForm;
        this.volume = volume;
        this.issue = issue;
        this.pages = pages;
    }

    //3. getter and setter methods
    public String getJournalShortForm() {
        return journalShortForm;
    }

    public void setJournalShortForm(String journalShortForm) {
        this.journalShortForm = journalShortForm;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    //4. other methods

    @Override
    public String citationDetailsAsString() {
        return "Citation type: \n" + citationType.getDescription() + "\n\n" +
                "Title: \n" + title + "\n\n" +
                "Author(s): \n" + author + "\n\n" +
                "Journal: \n" + journal + "\n\n" +
                "Journal abbreviation: \n" + journalShortForm + "\n\n" +
                "Year: \n" + year + "\n\n" +
                "Volume: \n" + volume + "\n\n" +
                "Issue: \n" + issue + "\n\n" +
                "Pages: \n" + pages + "\n\n" +
                "DOI: \n" + doi + "\n\n";
    }

    public String toCsvString(){
        return citationType + ";" +
                title + ";" +
                author.replace("; ", " and ") + ";" +
                journal + ";" +
                journalShortForm + ";" +
                year + ";" +
                volume + ";" +
                issue + ";" +
                pages + ";" +
                doi + ";" +
                String.join(",", pdfFilePaths);
    }

    @Override
    public String exportAsBibTexString(){
        String bibTexReference = createBibTexReference();

        return String.format(AppTexts.JOURNAL_ARTICLE_BIB_TEX_EXPORT_PROMPT,
                bibTexReference,
                this.author,
                this.title,
                this.journal,
                this.volume,
                this.issue,
                this.pages,
                "https://doi.org/" + this.doi,
                this.year);
    }

    public String createPubMedSearchTerm(){
        String[] authors = this.author.split(" and ");
        String lastAuthor = authors[authors.length-1];

        return this.journal.replaceAll(" ", "+") + "|" +
                this.year + "|" +
                this.volume + "|" +
                this.pages + "|" +
                lastAuthor.split(",")[0] + "|Art1|";
    }
}
