package de.apaschold.demo.model;

public class Unpublished extends Article {
    //0. constants

    //1. attributes

    //2. constructors
    public Unpublished(){
        super.setArticleType(ArticleType.UNPUBLISHED);
        super.setJournal(" - ");
        super.setDoi(" - ");
    }

    public Unpublished(String title, String author, int year, String pdfFilePath) {
        super(ArticleType.UNPUBLISHED, title, author, " - ", year, " - ", pdfFilePath);
    }

    //4. other methods

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "type='" + articleType + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", pdfFilePath='" + pdfFilePath + '\'' +
                '}';
    }

    public String toCsvString(){
        return articleType + ";" +
                title + ";" +
                author.replace("; ", " and ") + ";" +
                year + ";" +
                pdfFilePath;
    }
}
