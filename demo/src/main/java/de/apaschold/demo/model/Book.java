package de.apaschold.demo.model;

import de.apaschold.demo.additionals.AppTexts;

public class Book extends ArticleReference {
    //0. constants

    //1. attributes
    private int volume;

    //2. constructors
    public Book(){
        super.setArticleType(ArticleType.BOOK);
        this.volume = AppTexts.NUMBER_PLACEHOLDER;
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
                ", pdfFilePath='" + String.join(",",pdfFilePath) + '\'' +
                '}';
    }

    @Override
    public String toCsvString(){
        return articleType + ";" +
                title + ";" +
                author.replace("; ", " and ") + ";" +
                journal + ";" +
                year + ";" +
                volume + ";" +
                doi + ";" +
                String.join(",",pdfFilePath);
    }

    @Override
    public String exportAsBibTexString(){
        String bibTexReference = createBibTexReference();

        return String.format(AppTexts.BOOK_BIB_TEX_EXPORT_PROMPT,
                bibTexReference,
                this.author,
                this.title,
                this.journal,
                "https://doi.org/" + this.doi,
                this.year);
    }
}
