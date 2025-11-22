package de.apaschold.demo.model;

/**
 * <h2>ArticleType</h2>
 * <li>Enumeration representing different types of article references.</li>
 */

public enum CitationType {
    JOURNAL_ARTICLE ("Journal Article"),
    BOOK ("Book"),
    BOOK_SECTION("Book Chapter"),
    THESIS ("Phd Thesis"),
    PATENT ("Patent"),
    UNPUBLISHED ("Unpublished");

    private final String description;

    CitationType(String description) { this.description = description;}

    public String getDescription() { return description;}

    /**
     * <h2>getArticleTypeFromBibTexImport</h2>
     * <li>Static method to map BibTeX article type strings to {@link CitationType} enum values.</li>
     *
     * @param bibTexArticleType String representing the BibTeX article type
     * @return Corresponding CitationType enum value, or null if no match is found
     */
    public static CitationType getCitationTypeFromBibTexImport(String bibTexArticleType){

        return switch (bibTexArticleType) {
            case "@article" -> JOURNAL_ARTICLE;
            case "@book" -> BOOK;
            case "@inbook" -> BOOK_SECTION;
            case "@phdthesis" -> THESIS;
            case "@misc" -> PATENT;
            case "@unpublished" -> UNPUBLISHED;
            default -> null;
        };
    }
}
