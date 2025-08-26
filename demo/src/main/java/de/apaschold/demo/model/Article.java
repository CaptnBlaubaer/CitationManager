package de.apaschold.demo.model;

public abstract class Article {
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
    public Article(ArticleType type, String title, String author, String journal, int year, String doi, String pdfFilePath) {
        this.articleType = type;
        this.title = title;
        this.author = author;
        this.journal = journal;
        this.year = year;
        this.doi = doi;
        this.pdfFilePath = pdfFilePath;
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
}
