package de.apaschold.demo.model;

public class BookSection extends Article {
    //0. constants

    //1. attributes
    private int volume;
    private String booktitle;
    private String editor;
    private String pages;

    //2. constructors
    public BookSection(){
        super.setArticleType(ArticleType.BOOK_SECTION);
    }

    public BookSection(String title, String author, String booktitle, String editor, String journal, int year, int volume, String pages, String doi, String pdfFilePath) {
        super(ArticleType.BOOK_SECTION, title, author, journal, year, doi, pdfFilePath);
        this.volume = volume;
        this.booktitle = booktitle;
        this.editor = editor;
        this.pages = pages;
    }

    //3. getter and setter methods
    public int getVolume(){ return this.volume;}

    public void setVolume(int volume){ this.volume = volume;}

    public String getBooktitle() { return booktitle;}

    public void setBooktitle(String booktitle) { this.booktitle = booktitle;}

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
                ", booktitle='" + booktitle + "\'" +
                ", editor='" + editor + '\'' +
                ", publisher='" + journal + '\'' +
                ", year=" + year +
                ", volume=" + volume +
                ", pages=" + pages +
                ", doi='" + doi + '\'' +
                ", url='" + pdfFilePath + '\'' +
                '}';
    }

    public String toCsvString(){
        return articleType + ";" +
                title + ";" +
                author.replace("; ", " and ") + ";" +
                booktitle + ";" +
                editor.replace("; ", " and ") + ";" +
                journal + ";" +
                year + ";" +
                volume + ";" +
                pages + ";" +
                doi + ";" +
                pdfFilePath;
    }
}
