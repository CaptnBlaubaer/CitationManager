package de.apaschold.demo.model;

import de.apaschold.demo.additionals.AppTexts;

/**
 * <h2>PhdThesis</h2>
 * <li>Represents a PhD thesis citation, extending the {@link Citation} class.</li>
 */

public class PhdThesis extends Citation {
    //0. constants

    //1. attributes

    //2. constructors
    public PhdThesis(){
        super.setCitationType(CitationType.THESIS);
    }

    public PhdThesis(String title, String author, int year, String doi, String pdfFilePath) {
        super(CitationType.THESIS, title, author, " - ", year, doi, pdfFilePath);
    }

    //4. other methods

    @Override
    public String citationDetailsAsString() {
        return "Citation type: \n" + citationType.getDescription() + "\n\n" +
                "Title: \n" + title + "\n\n" +
                "Author(s): \n" + author + "\n\n" +
                "Year: \n" + year + "\n\n" +
                "DOI: \n" + doi + "\n\n";
    }

    @Override
    public String toCsvString(){
        return citationType + ";" +
                title + ";" +
                author.replace("; ", " and ") + ";" +
                year + ";" +
                doi + ";" +
                String.join(",",pdfFilePath);
    }

    @Override
    public String exportAsBibTexString(){
        String bibTexReference = createBibTexReference();

        return String.format(AppTexts.THESIS_BIB_TEX_EXPORT_PROMPT,
                bibTexReference,
                this.author,
                this.title,
                "https://doi.org/" + this.doi,
                this.year);
    }
}
