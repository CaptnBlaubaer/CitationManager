package de.apaschold.demo.model;

import de.apaschold.demo.additionals.AppTexts;

/**
 * <h2>Patent</h2>
 * <li>Represents a patent article reference, extending the ArticleReference class.</li>
 */

public class Patent extends Citation {
    //0. constants

    //1. attributes

    //2. constructors
    public Patent(){
        super.setArticleType(ArticleType.PATENT);
    }

    public Patent(String title, String author, int year, String doi, String pdfFilePath) {
        super(ArticleType.PATENT, title, author, " - ", year, doi, pdfFilePath);
    }

    //4. other methods

    @Override
    public String citationDetailsAsString() {
        return "Citation type: \n" + articleType.getDescription() + "\n\n" +
                "Title: \n" + title + "\n\n" +
                "Author(s): \n" + author + "\n\n" +
                "Year: \n" + year + "\n\n" +
                "DOI: \n" + doi + "\n\n";
    }

    @Override
    public String toCsvString(){
        return articleType + ";" +
                title + ";" +
                author.replace("; ", " and ") + ";" +
                year + ";" +
                doi + ";" +
                String.join(",",pdfFilePath);
    }

    @Override
    public String exportAsBibTexString(){
        String bibTexReference = createBibTexReference();

        return String.format(AppTexts.PATENT_BIB_TEX_EXPORT_PROMPT,
                bibTexReference,
                this.author,
                this.title,
                "https://doi.org/" + this.doi,
                this.year);
    }
}
