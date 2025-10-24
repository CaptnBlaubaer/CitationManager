package de.apaschold.demo.model;

import de.apaschold.demo.additionals.AppTexts;

/**
 * <h2>Unpublished</h2>
 * <li>Represents an unpublished article reference, extending the ArticleReference class.</li>
 */

public class Unpublished extends ArticleReference {
    //0. constants

    //1. attributes

    //2. constructors
    public Unpublished(){
        super.setArticleType(ArticleType.UNPUBLISHED);
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
                ", pdfFilePath='" + String.join(",",pdfFilePath) + '\'' +
                '}';
    }

    @Override
    public String toCsvString(){
        return articleType + ";" +
                title + ";" +
                author.replace("; ", " and ") + ";" +
                year + ";" +
                String.join(",",pdfFilePath);
    }

    @Override
    public String exportAsBibTexString(){
        String bibTexReference = createBibTexReference();

        return String.format(AppTexts.UNPUBLISHED_BIB_TEX_EXPORT_PROMPT,
                bibTexReference,
                this.author,
                this.title,
                this.year);
    }
}
