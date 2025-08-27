package de.apaschold.demo.model;

public class Book extends Article {
    //0. constants

    //1. attributes
    private int volume;

    //2. constructors
    public Book(){
        super.setArticleType(ArticleType.BOOK);
    }

    public Book(String title, String author, String journal, int year, int volume, String doi, String pdfFilePath) {
        super(ArticleType.BOOK, title, author, journal, year, doi, pdfFilePath);
        this.volume = volume;
    }

    //3. getter and setter methods
    public int getVolume(){ return this.volume;}

    public void setVolume(int volume){ this.volume = volume;}

    //4. other methods

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "type='" + articleType + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + journal + '\'' +
                ", year=" + year +
                ", volume=" + volume +
                ", doi='" + doi + '\'' +
                ", url='" + pdfFilePath + '\'' +
                '}';
    }

    public String toCsvString(){
        return articleType + ";" +
                title + ";" +
                author.replace("; ", " and ") + ";" +
                journal + ";" +
                year + ";" +
                volume + ";" +
                doi + ";" +
                pdfFilePath;
    }
}
