package de.apaschold.demo.model;

public class BookSection extends Article {
    //0. constants

    //1. attributes
    private int volume;
    private String bookTitle;
    private String editor;
    private String pages;

    //2. constructors
    public BookSection(){
        super.setArticleType(ArticleType.BOOK_SECTION);
    }

    public BookSection(String title, String author, String bookTitle, String editor, String journal, int year, int volume, String pages, String doi, String pdfFilePath) {
        super(ArticleType.BOOK_SECTION, title, author, journal, year, doi, pdfFilePath);
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
        return "ArticleInfo{" +
                "type='" + articleType + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", book title='" + bookTitle + '\'' +
                ", editor='" + editor + '\'' +
                ", publisher='" + journal + '\'' +
                ", year=" + year +
                ", volume=" + volume +
                ", pages=" + pages +
                ", doi='" + doi + '\'' +
                ", pdfFilePath='" + pdfFilePath + '\'' +
                '}';
    }

    public String toCsvString(){
        return articleType + ";" +
                title + ";" +
                author.replace("; ", " and ") + ";" +
                bookTitle + ";" +
                editor.replace("; ", " and ") + ";" +
                journal + ";" +
                year + ";" +
                volume + ";" +
                pages + ";" +
                doi + ";" +
                pdfFilePath;
    }
}
