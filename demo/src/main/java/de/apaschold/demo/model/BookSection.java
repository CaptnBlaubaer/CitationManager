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
    public String citationDetailsAsString() {
        return "Citation type: \n" + citationType.getDescription() + "\n\n" +
                "Chapter title: \n" + title + "\n\n" +
                "Author(s): \n" + author + "\n\n" +
                "Book title: \n" + journal + "\n\n" +
                "Editor(s): \n" + editor + "\n\n" +
                "Year: \n" + year + "\n\n" +
                "Volume: \n" + volume + "\n\n" +
                "Pages: \n" + pages + "\n\n" +
                "DOI: \n" + doi + "\n\n";
    }

    @Override
    public String toCsvString(){
        return citationType + ";" +
                title + ";" +
                author.replace("; ", " and ") + ";" +
                bookTitle + ";" +
                editor.replace("; ", " and ") + ";" +
                journal + ";" +
                year + ";" +
                volume + ";" +
                pages + ";" +
                doi + ";" +
                String.join(",",pdfFilePath);
    }

    @Override
    public String exportAsBibTexString(){
        String bibTexReference = createBibTexReference();

        return String.format(AppTexts.BOOK_SECTION_BIB_TEX_EXPORT_PROMPT,
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
