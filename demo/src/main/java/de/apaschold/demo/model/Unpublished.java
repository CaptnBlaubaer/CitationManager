package de.apaschold.demo.model;

import de.apaschold.demo.additionals.AppTexts;

/**
 * <h2>Unpublished</h2>
 * <li>Represents an unpublished citation, extending the {@link Citation} class.</li>
 */

public class Unpublished extends Citation {
    //0. constants

    //1. attributes

    //2. constructors
    public Unpublished(){
        super.setCitationType(CitationType.UNPUBLISHED);
    }

    public Unpublished(String title, String author, int year, String pdfFilePath) {
        super(CitationType.UNPUBLISHED, title, author, " - ", year, " - ", pdfFilePath);
    }

    //4. other methods

    @Override
    public String citationDetailsAsString() {
        return "Citation type: \n" + citationType.getDescription() + "\n\n" +
                "Title: \n" + title + "\n\n" +
                "Author(s): \n" + author + "\n\n" +
                "Year: \n" + year + "\n\n";
    }

    @Override
    public String toCsvString(){
        return citationType + ";" +
                title + ";" +
                author.replace("; ", " and ") + ";" +
                year + ";" +
                String.join(",", pdfFilePaths);
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
