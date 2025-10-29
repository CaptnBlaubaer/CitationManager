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
    public String toString() {
        return "Citation type: " + citationType.getDescription() + "\n" +
                "Title: " + title + "\n" +
                "Author(s): " + author + "\n" +
                "Year: " + year + "\n" +
                "URL: " + doi + "\n";
    }

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
        return String.format(AppTexts.CSV_STRING_TEMPLATE,
                citationType + "",
                title,
                author.replace("; ", " and "),
                AppTexts.PLACEHOLDER,
                year,
                doi,
                String.join(",",pdfFilePaths),
                AppTexts.PLACEHOLDER,
                AppTexts.NUMBER_PLACEHOLDER,
                AppTexts.NUMBER_PLACEHOLDER,
                AppTexts.PLACEHOLDER,
                AppTexts.PLACEHOLDER,
                AppTexts.PLACEHOLDER
        );
    }

    @Override
    public String exportAsBibTexString(){
        String bibTexReference = createBibTexReference();

        return String.format(AppTexts.PATENT_BIB_TEX_EXPORT_TEMPLATE,
                bibTexReference,
                this.author,
                this.title,
                "https://doi.org/" + this.doi,
                this.year);
    }
}
