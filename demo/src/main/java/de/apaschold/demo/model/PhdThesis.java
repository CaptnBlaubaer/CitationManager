package de.apaschold.demo.model;

public class PhdThesis extends Article{
    //0. constants

    //1. attributes

    //2. constructors
    public PhdThesis(){
        super.setArticleType(ArticleType.THESIS);
        super.setJournal(" - ");
    }

    public PhdThesis(String title, String author, int year, String doi, String pdfFilePath) {
        super(ArticleType.THESIS, title, author, " - ", year, doi, pdfFilePath);
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
                ", url='" + pdfFilePath + '\'' +
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
