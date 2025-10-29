package de.apaschold.demo.model;

import de.apaschold.demo.additionals.AppTexts;

/**
 * <h2>Book</h2>
 * <li>Represents a book citation, extending the {@link Citation} class.</li>
 */
public class Book extends Citation {
    //0. constants

    //1. attributes
    private int volume;

    //2. constructors
    public Book(){
        super.setCitationType(CitationType.BOOK);
        this.volume = AppTexts.NUMBER_PLACEHOLDER;
    }

    public Book(String title, String author, String journal, int year, int volume, String doi, String pdfFilePath) {
        super(CitationType.BOOK, title, author, journal, year, doi, pdfFilePath);
        this.volume = volume;
    }

    //3. getter and setter methods
    public int getVolume(){ return this.volume;}

    public void setVolume(int volume){ this.volume = volume;}

    //4. other methods
    @Override
    public String toString() {
        return "Citation type: " + citationType.getDescription() + "\n" +
                "Title: " + title + "\n" +
                "Author(s): " + author + "\n" +
                "Publisher: " + journal + "\n" +
                "Year: " + year + "\n" +
                "Volume: " + volume + "\n" +
                "DOI: " + doi + "\n";
    }

    @Override
    public String citationDetailsAsString() {
        return "Citation type: \n" + citationType.getDescription() + "\n\n" +
                "Title: \n" + title + "\n\n" +
                "Author(s): \n" + author.replaceAll("; ", "\n") + "\n\n" +
                "Publisher: \n" + journal + "\n\n" +
                "Year: \n" + year + "\n\n" +
                "Volume: \n" + volume + "\n\n" +
                "DOI: \n" + doi + "\n\n";
    }

    @Override
    public String toCsvString(){
        return String.format(AppTexts.CSV_STRING_TEMPLATE,
                citationType + "",
                title,
                author.replace("; ", " and "),
                journal,
                year,
                doi,
                String.join(",",pdfFilePaths),
                AppTexts.PLACEHOLDER,
                volume,
                AppTexts.NUMBER_PLACEHOLDER,
                AppTexts.PLACEHOLDER,
                AppTexts.PLACEHOLDER,
                AppTexts.PLACEHOLDER
        );
    }

    @Override
    public String exportAsBibTexString(){
        String bibTexReference = createBibTexReference();

        return String.format(AppTexts.BOOK_BIB_TEX_EXPORT_TEMPLATE,
                bibTexReference,
                this.author,
                this.title,
                this.journal,
                "https://doi.org/" + this.doi,
                this.year);
    }
}
