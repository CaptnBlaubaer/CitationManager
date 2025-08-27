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

    public static ArticleType getArticleTypeFromBibTexImport(String bibTexArticleType){
        ArticleType returnType = null;

        if (bibTexArticleType.equals("@article")){
            returnType = JOURNAL_ARTICLE;
        } else if (bibTexArticleType.equals("@book")){
            returnType = BOOK;
        } else if (bibTexArticleType.equals("@inbook")){
            returnType = BOOK_CHAPTER;
        } else if (bibTexArticleType.equals("@phdthesis")){
            returnType = THESIS;
        }

        return returnType;
    }
}
