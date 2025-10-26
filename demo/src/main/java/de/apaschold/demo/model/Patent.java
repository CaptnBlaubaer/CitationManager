package de.apaschold.demo.model;

import de.apaschold.demo.additionals.AppTexts;

/**
 * <h2>Patent</h2>
 * <li>Represents a patent citation, extending the {@link Citation} class.</li>
 */

public class Patent extends Citation {
    //0. constants

    //1. attributes

    //2. constructors
    public Patent(){
        super.setCitationType(CitationType.PATENT);
    }

    public Patent(String title, String author, int year, String doi, String pdfFilePath) {
        super(CitationType.PATENT, title, author, " - ", year, doi, pdfFilePath);
    }

    //4. other methods

    @Override
    public String citationDetailsAsString() {
        return "Citation type: \n" + citationType.getDescription() + "\n\n" +
                "Title: \n" + title + "\n\n" +
                "Author(s): \n" + author.replaceAll("; ", "\n") + "\n\n" +
                "Year: \n" + year + "\n\n" +
                "URL: \n" + doi + "\n\n";
    }

    @Override
    public String toCsvString(){
        return citationType + ";" +
                title + ";" +
                author.replace("; ", " and ") + ";" +
                year + ";" +
                doi + ";" +
                String.join(",", pdfFilePaths);
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
