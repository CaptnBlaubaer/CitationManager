package de.apaschold.demo.model;

public class JournalArticle {
    //0. constants

    //1. attributes
    private ArticleType articleType;
    private String title;
    private String author;
    private String journal;
    private String journalShortForm;
    private int volume;
    private int issue;
    private int year;
    private String pages;
    private String doi;
    private String pdfFilePath;

    //2. constructors
    public JournalArticle(ArticleType type, String title, String author, String journal, String journalShortForm, int year, int volume, int issue, String pages, String doi) {
        this.articleType = type;
        this.title = title;
        this.author = author;
        this.journal = journal;
        this.journalShortForm = journalShortForm;
        this.year = year;
        this.volume = volume;
        this.issue = issue;
        this.pages = pages;
        this.doi = doi;
    }

    //3. getter and setter methods
    public ArticleType getArticleType() {
        return articleType;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getPdfFilePath() {
        return pdfFilePath;
    }

    public void setPdfFilePath(String pdfFilePath) {
        this.pdfFilePath = pdfFilePath;
    }

    //4. other methods

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", journal='" + journal + '\'' +
                ", journalShortForm='" + journalShortForm + '\'' +
                ", volume=" + volume +
                ", issue=" + issue +
                ", year=" + year +
                ", doi='" + doi + '\'' +
                '}';
    }
}
