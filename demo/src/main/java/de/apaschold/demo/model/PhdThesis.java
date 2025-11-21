package de.apaschold.demo.model;

import de.apaschold.demo.additionals.AppTexts;

/**
 * <h2>PhdThesis</h2>
 * <li>Represents a PhD thesis citation, extending the {@link AbstractCitation} class.</li>
 */

public class PhdThesis extends AbstractCitation {
    //0. constants

    //1. attributes

    //2. constructors
    public PhdThesis(){
        super.setCitationType(CitationType.THESIS);
    }

    public PhdThesis(int id, String title, String author, int year, String doi, String pdfFilePath) {
        super(id, CitationType.THESIS, title, author, " - ", year, doi, pdfFilePath);
    }

    //4. other methods
    @Override
    public String toString() {
        return "Citation type: " + citationType.getDescription() + "\n" +
                "Title: " + title + "\n" +
                "Author(s): " + author + "\n" +
                "Year: " + year + "\n" +
                "DOI: " + doi + "\n";
    }

    @Override
    public String citationDetailsAsString() {
        return "Citation type: \n" + citationType.getDescription() + "\n\n" +
                "Title: \n" + title + "\n\n" +
                "Author(s): \n" + author.replaceAll("; ", "\n") + "\n\n" +
                "Year: \n" + year + "\n\n" +
                "DOI: \n" + doi + "\n\n";
    }

    @Override
    public String toCsvString(){
        return String.format(AppTexts.CSV_STRING_TEMPLATE,
                id,
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

        return String.format(AppTexts.THESIS_BIB_TEX_EXPORT_TEMPLATE,
                bibTexReference,
                this.author,
                this.title,
                "https://doi.org/" + this.doi,
                this.year);
    }
}
