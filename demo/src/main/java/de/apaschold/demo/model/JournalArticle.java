package de.apaschold.demo.model;

import de.apaschold.demo.additionals.AppTexts;

public class JournalArticle extends ArticleReference {
    //0. constants

    //1. attributes
    private String journalShortForm;
    private int volume;
    private int issue;
    private String pages;

    //2. constructors
    public JournalArticle(){
        super.setArticleType(ArticleType.JOURNAL_ARTICLE);
        this.journalShortForm = AppTexts.PLACEHOLDER;
        this.volume = AppTexts.NUMBER_PLACEHOLDER;
        this.issue = AppTexts.NUMBER_PLACEHOLDER;
        this.pages = AppTexts.PLACEHOLDER;
    }

    public JournalArticle(String title, String author, String journal, String journalShortForm, int year, int volume, int issue, String pages, String doi, String pdfFilePath) {
        super(ArticleType.JOURNAL_ARTICLE, title, author, journal, year, doi, pdfFilePath);
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
    public String toString() {
        return "ArticleInfo{" +
                "type='" + articleType + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", journal='" + journal + '\'' +
                ", journalShortForm='" + journalShortForm + '\'' +
                ", year=" + year +
                ", volume=" + volume +
                ", issue=" + issue +
                ", pages=" + pages +
                ", doi='" + doi + '\'' +
                ", pdfFilePath='" + String.join(",",pdfFilePath) + '\'' +
                '}';
    }

    public String toCsvString(){
        return articleType + ";" +
                title + ";" +
                author.replace("; ", " and ") + ";" +
                journal + ";" +
                journalShortForm + ";" +
                year + ";" +
                volume + ";" +
                issue + ";" +
                pages + ";" +
                doi + ";" +
                String.join(",",pdfFilePath);
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
