package de.apaschold.demo.model;

public class JournalArticle extends Article {
    //0. constants

    //1. attributes
    private String journalShortForm;
    private int volume;
    private int issue;
    private String pages;

    //2. constructors
    public JournalArticle(){
        super.setArticleType(ArticleType.JOURNAL_ARTICLE);
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
                ", pdfFilePath='" + pdfFilePath + '\'' +
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
                pdfFilePath;
    }
}
