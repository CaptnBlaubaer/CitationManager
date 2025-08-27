package de.apaschold.demo.model;

public enum ArticleType {
    JOURNAL_ARTICLE ("Journal Article"),
    BOOK ("Book"),
    BOOK_CHAPTER ("Book Chapter"),
    WEBSITE ("Website"),
    THESIS ("Thesis");

    private final String description;

    ArticleType(String description) { this.description = description;}

    public String getDescription() { return description;}
}
