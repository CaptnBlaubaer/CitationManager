package de.apaschold.demo.model;

import de.apaschold.demo.additionals.AppTexts;

/**
 * <h2>BookSection</h2>
 * <li>Represents a book section (chapter) citation, extending the {@link Citation} class.</li>
 */
public class BookSection extends Citation {
    //0. constants

    //1. attributes
    private int volume;
    private String bookTitle;
    private String editor;
    private String pages;

    //2. constructors
    public BookSection(){
        super.setCitationType(CitationType.BOOK_SECTION);
        this.volume = AppTexts.NUMBER_PLACEHOLDER;
        this.bookTitle = AppTexts.PLACEHOLDER;
        this.editor = AppTexts.PLACEHOLDER;
        this.pages = AppTexts.PLACEHOLDER;
    }

    public BookSection(String title, String author, String bookTitle, String editor, String journal, int year, int volume, String pages, String doi, String pdfFilePath) {
        super(CitationType.BOOK_SECTION, title, author, journal, year, doi, pdfFilePath);
        this.volume = volume;
        this.bookTitle = bookTitle;
        this.editor = editor;
        this.pages = pages;
    }

    //3. getter and setter methods
    public int getVolume(){ return this.volume;}

    public void setVolume(int volume){ this.volume = volume;}

    public String getBookTitle() { return bookTitle;}

    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle;}

    public String getEditor() { return editor;}

    public void setEditor(String editor) { this.editor = editor;}

    public String getPages() { return pages;}

    public void setPages(String pages) { this.pages = pages;}

    //4. other methods
    @Override
    public String toString() {
        return "Citation type: " + citationType.getDescription() + "\n" +
                "Chapter title: " + title + "\n" +
                "Author(s): " + author + "\n" +
                "Book title: " + bookTitle + "\n" +
                "Editor(s): " + editor + "\n" +
                "Publisher: " + journal + "\n" +
                "Year: " + year + "\n" +
                "Volume: " + volume + "\n" +
                "Pages: " + pages + "\n" +
                "DOI: " + doi + "\n";
    }

    @Override
    public String citationDetailsAsString() {
        return "Citation type: \n" + citationType.getDescription() + "\n\n" +
                "Chapter title: \n" + title + "\n\n" +
                "Author(s): \n" + author.replaceAll("; ", "\n") + "\n\n" +
                "Book title: \n" + journal + "\n\n" +
                "Editor(s): \n" + editor.replaceAll("; ", "\n") + "\n\n" +
                "Year: \n" + year + "\n\n" +
                "Volume: \n" + volume + "\n\n" +
                "Pages: \n" + pages + "\n\n" +
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
                pages,
                bookTitle,
                editor
        );
    }

    @Override
    public String exportAsBibTexString(){
        String bibTexReference = createBibTexReference();

        return String.format(AppTexts.BOOK_SECTION_BIB_TEX_EXPORT_TEMPLATE,
                bibTexReference,
                this.author,
                this.title,
                this.bookTitle,
                this.editor,
                this.journal,
                this.volume,
                this.pages,
                "https://doi.org/" + this.doi,
                this.year);
    }
}
