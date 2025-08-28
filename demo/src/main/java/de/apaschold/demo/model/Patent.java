package de.apaschold.demo.model;

public class Patent extends Article{
    //0. constants

    //1. attributes

    //2. constructors
    public Patent(){
        super.setArticleType(ArticleType.PATENT);
        super.setJournal(" - ");
    }

    public Patent(String title, String author, int year, String doi, String pdfFilePath) {
        super(ArticleType.PATENT, title, author, " - ", year, doi, pdfFilePath);
    }

    //4. other methods

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "type='" + articleType + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", doi='" + doi + '\'' +
                ", pdfFilePath='" + pdfFilePath + '\'' +
                '}';
    }

    public String toCsvString(){
        return articleType + ";" +
                title + ";" +
                author.replace("; ", " and ") + ";" +
                year + ";" +
                doi + ";" +
                pdfFilePath;
    }
}
