package de.apaschold.demo.model;

/**
 * <h2>ArticleType</h2>
 * <li>Enumeration representing different types of article references.</li>
 */

public enum ArticleType {
    JOURNAL_ARTICLE ("Journal Article"),
    BOOK ("Book"),
    BOOK_SECTION("Book Chapter"),
    WEBSITE ("Website"),
    THESIS ("Thesis"),
    PATENT ("Patent"),
    UNPUBLISHED ("Unpublished");

    private final String description;

    ArticleType(String description) { this.description = description;}

    public String getDescription() { return description;}

    /**
     * <h2>getArticleTypeFromBibTexImport</h2>
     * <li>Static method to map BibTeX article type strings to ArticleType enum values.</li>
     *
     * @param bibTexArticleType String representing the BibTeX article type
     * @return Corresponding ArticleType enum value, or null if no match is found
     */
    public static ArticleType getArticleTypeFromBibTexImport(String bibTexArticleType){
        ArticleType returnType = null;

        if (bibTexArticleType.equals("@article")){
            returnType = JOURNAL_ARTICLE;
        } else if (bibTexArticleType.equals("@book")){
            returnType = BOOK;
        } else if (bibTexArticleType.equals("@inbook")){
            returnType = BOOK_SECTION;
        } else if (bibTexArticleType.equals("@phdthesis")){
            returnType = THESIS;
        } else if (bibTexArticleType.equals("@misc")){
            returnType = PATENT;
        } else if (bibTexArticleType.equals("@unpublished")){
            returnType = UNPUBLISHED;
        }

        return returnType;
    }
}
