package de.apaschold.demo.logic;

import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.model.*;

//TODO Create methods for patent and unpublished

/**
 * <h2>ArticleFactory</h2>
 * <p>Factory class for creating {@link ArticleReference} objects from different input formats.</p>
 */

public class ArticleFactory {
    //0. constants
    private static final String BIBTEX_TITLE_PROMPT = "title={";
    private static final String BIBTEX_AUTHOR_PROMPT = "author={";
    private static final String BIBTEX_BOOK_TITLE_PROMPT = "booktitle={";
    private static final String BIBTEX_EDITOR_PROMPT = "editor={";
    private static final String BIBTEX_JOURNAL_PROMPT = "journal={";
    private static final String BIBTEX_PUBLISHER_PROMPT = "publisher={";
    private static final String BIBTEX_VOLUME_PROMPT = "volume={";
    private static final String BIBTEX_ISSUE_PROMPT = "number={";
    private static final String BIBTEX_PAGES_PROMPT = "pages={";
    private static final String BIBTEX_YEAR_PROMPT = "year={";
    private static final String BIBTEX_DOI_PROMPT = "DOI={";
    private static final String BIBTEX_URL_PROMPT = "url={";
    private static final String BIBTEX_LINE_END_PROMPT = "},";

    //1. attributes

    //2. constructors
    private ArticleFactory(){}

    //3. factory methods
    //methods for csv
    /**
     * <h2>createArticleReferenceFromCsvLine</h2>
     * <li>Creates an {@link ArticleReference} object from a CSV line.</li>
     * <li>Chooses dynamically method for the respective article Type</li>
     *
     * @param csvLine the CSV line containing article details
     * @return the created {@link ArticleReference} object
     */
    public static ArticleReference createArticleReferenceFromCsvLine(String csvLine){
        String[] separatedCsvLine = csvLine.split(";");

        ArticleType type = ArticleType.valueOf(separatedCsvLine[0]);

        ArticleReference newArticle = switch (type) {
            case JOURNAL_ARTICLE -> createJournalArticleFromCsvLine(separatedCsvLine);
            case BOOK -> createBookFromCsvLine(separatedCsvLine);
            case BOOK_SECTION ->  createBookSectionFromCsvLine(separatedCsvLine);
            case THESIS -> createPhdThesisFromCsvLine(separatedCsvLine);
            case PATENT -> createPatentFromCsvLine(separatedCsvLine);
            case UNPUBLISHED -> createUnpublishedFromCsvLine(separatedCsvLine);
            default -> null;
        };

        return newArticle;
    }

    /**
     * <h2>createJournalArticleFromCsvLine</h2>
     * <li>Creates a {@link JournalArticle} object from a CSV line.</li>
     *
     * @param separatedCsvLine the CSV line containing article details, already split into an array
     * @return the created {@link JournalArticle} object
     */
    private static JournalArticle createJournalArticleFromCsvLine(String[] separatedCsvLine){
        String title = separatedCsvLine[1];
        String authors = separatedCsvLine[2].replace(" and ", "; ");
        String journal = separatedCsvLine[3];
        String journalShortForm = separatedCsvLine[4];
        int year = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[5]);
        int volume = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[6]);
        int issue = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[7]);
        String pages = separatedCsvLine[8];
        String doi = separatedCsvLine[9];
        String pdfFilePath = separatedCsvLine[10];

        return new JournalArticle(title, authors, journal, journalShortForm, year, volume, issue, pages, doi, pdfFilePath);
    }

    /**
     * <h2>createBookFromCsvLine</h2>
     * <li>Creates a {@link Book} object from a CSV line.</li>
     *
     * @param separatedCsvLine the CSV line containing article details, already split into an array
     * @return the created {@link Book} object
     */
    private static Book createBookFromCsvLine(String[] separatedCsvLine){
        String title = separatedCsvLine[1];
        String authors = separatedCsvLine[2].replace(" and ", "; ");
        String journal = separatedCsvLine[3];
        int year = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[4]);
        int volume = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[5]);
        String doi = separatedCsvLine[6];
        String pdfFilePath = separatedCsvLine[7];
        return new Book(title, authors, journal, year, volume, doi, pdfFilePath);
    }

    /**
     * <h2>createBookSectionFromCsvLine</h2>
     * <li>Creates a {@link BookSection} object from a CSV line.</li>
     *
     * @param separatedCsvLine the CSV line containing article details, already split into an array
     * @return the created {@link BookSection} object
     */
    private static BookSection createBookSectionFromCsvLine(String[] separatedCsvLine){
        String title = separatedCsvLine[1];
        String authors = separatedCsvLine[2].replace(" and ", "; ");
        String bookTitle = separatedCsvLine[3];
        String editor = separatedCsvLine[4].replace(" and ", "; ");
        String journal = separatedCsvLine[5];
        int year = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[6]);
        int volume = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[7]);
        String pages = separatedCsvLine[8];
        String doi = separatedCsvLine[9];
        String pdfFilePath = separatedCsvLine[10];

        return new BookSection(title, authors, bookTitle, editor, journal, year, volume, pages, doi, pdfFilePath);
    }

    /**
     * <h2>createPhdThesisFromCsvLine</h2>
     * <li>Creates a {@link PhdThesis} object from a CSV line.</li>
     *
     * @param separatedCsvLine the CSV line containing article details, already split into an array
     * @return the created {@link PhdThesis} object
     */
    private static ArticleReference createPhdThesisFromCsvLine(String[] separatedCsvLine) {
        String title = separatedCsvLine[1];
        String authors = separatedCsvLine[2].replace(" and ", "; ");
        int year = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[3]);
        String doi = separatedCsvLine[4];
        String pdfFilePath = separatedCsvLine[5];
        return new PhdThesis(title, authors, year, doi, pdfFilePath);
    }

    /**
     * <h2>createPatentFromCsvLine</h2>
     * <li>Creates a {@link Patent} object from a CSV line.</li>
     *
     * @param separatedCsvLine the CSV line containing article details, already split into an array
     * @return the created {@link Patent} object
     */
    private static ArticleReference createPatentFromCsvLine(String[] separatedCsvLine) {
        String title = separatedCsvLine[1];
        String authors = separatedCsvLine[2].replace(" and ", "; ");
        int year = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[3]);
        String doi = separatedCsvLine[4];
        String pdfFilePath = separatedCsvLine[5];
        return new Patent(title, authors, year, doi, pdfFilePath);
    }

    /**
     * <h2>createUnpublishedFromCsvLine</h2>
     * <li>Creates a {@link Unpublished} object from a CSV line.</li>
     *
     * @param separatedCsvLine the CSV line containing article details, already split into an array
     * @return the created {@link Unpublished} object
     */
    private static ArticleReference createUnpublishedFromCsvLine(String[] separatedCsvLine) {
        String title = separatedCsvLine[1];
        String authors = separatedCsvLine[2].replace(" and ", "; ");
        int year = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[3]);
        String pdfFilePath = separatedCsvLine[4];
        return new Unpublished (title, authors, year, pdfFilePath);
    }

    //methods for BibTex
    /**
     * <h2>createArticleFromBibTex</h2>
     * <li>Creates an {@link ArticleReference} object from a BibTex text.</li>
     * <li>Chooses dynamically method for the respective article Type</li>
     *
     * @param bibTexText the BibTex text containing article details
     * @return the created {@link ArticleReference} object
     */
    public static ArticleReference createArticleFromBibTex(String bibTexText){
        String[] articleTypeAndDetails = bibTexText.split("\\{", 2); //

        ArticleType importedArticleType = ArticleType.getArticleTypeFromBibTexImport(articleTypeAndDetails[0]);

        String[] articleDetails = articleTypeAndDetails[1].split("\n");

        ArticleReference importedArticle = switch (importedArticleType) {
            case JOURNAL_ARTICLE -> createJournalArticleFromBibTex(articleDetails);
            case BOOK -> createBookFromBibTex(articleDetails);
            case BOOK_SECTION -> createBookSectionFromBibTex(articleDetails);
            case THESIS -> createPhdThesisFromBibTex(articleDetails);
            case PATENT -> createPatentFromBibTex(articleDetails);
            case UNPUBLISHED -> createUnpublishedFromBibTex(articleDetails);
            default -> null;
        };

        return importedArticle;
    }

    /**
     * <h2>createJournalArticleFromBibTex</h2>
     * <li>Creates a {@link JournalArticle} object from a BibTex text.</li>
     *
     * @param articleDetails the BibTex text containing article details, already split into an array
     * @return the created {@link JournalArticle} object
     */
    private static ArticleReference createJournalArticleFromBibTex(String[] articleDetails) {
        JournalArticle importedJournalArticle = new JournalArticle();

        for (String rawDetail : articleDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END_PROMPT,"").strip();

            if(rawDetail.contains(BIBTEX_TITLE_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_PROMPT,"");
                importedJournalArticle.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_PROMPT,"").replace(" and ","; ");
                importedJournalArticle.setAuthors(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_JOURNAL_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_JOURNAL_PROMPT,"");
                importedJournalArticle.setJournal(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_VOLUME_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_VOLUME_PROMPT,"");
                importedJournalArticle.setVolume( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_ISSUE_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_ISSUE_PROMPT,"");
                importedJournalArticle.setIssue( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_YEAR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_YEAR_PROMPT,"");
                importedJournalArticle.setYear( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_PAGES_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_PAGES_PROMPT,"");
                importedJournalArticle.setPages(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_DOI_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_DOI_PROMPT,"");
                importedJournalArticle.setDoi(refinedDetail);
            }
        }

        return importedJournalArticle;
    }

    /**
     * <h2>createBookFromBibTex</h2>
     * <li>Creates a {@link Book} object from a BibTex text.</li>
     *
     * @param articleDetails the BibTex text containing article details, already split into an array
     * @return the created {@link Book} object
     */
    private static ArticleReference createBookFromBibTex(String[] articleDetails) {
        Book importedBook = new Book();

        for (String rawDetail : articleDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END_PROMPT,"").strip();

            if(rawDetail.contains(BIBTEX_TITLE_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_PROMPT,"");
                importedBook.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_PROMPT,"").replace(" and ","; ");
                importedBook.setAuthors(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_PUBLISHER_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_PUBLISHER_PROMPT,"");
                importedBook.setJournal(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_VOLUME_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_VOLUME_PROMPT,"");
                importedBook.setVolume( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_YEAR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_YEAR_PROMPT,"");
                importedBook.setYear( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_DOI_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_DOI_PROMPT,"");
                importedBook.setDoi(refinedDetail);
            }
        }

        return importedBook;
    }

    /**
     * <h2>createBookSectionFromBibTex</h2>
     * <li>Creates a {@link BookSection} object from a BibTex text.</li>
     *
     * @param articleDetails the BibTex text containing article details, already split into an array
     * @return the created {@link BookSection} object
     */
    private static ArticleReference createBookSectionFromBibTex(String[] articleDetails) {
        BookSection importedBookSection = new BookSection();

        for (String rawDetail : articleDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END_PROMPT,"").strip();

            if(rawDetail.contains(BIBTEX_BOOK_TITLE_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_BOOK_TITLE_PROMPT,"");
                importedBookSection.setBookTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_PROMPT,"").replace(" and ","; ");
                importedBookSection.setAuthors(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_TITLE_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_PROMPT,"");
                importedBookSection.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_EDITOR_PROMPT)) {
                refinedDetail = refinedDetail.replace(BIBTEX_EDITOR_PROMPT, "").replace(" and ", "; ");
                importedBookSection.setEditor(refinedDetail);
            }else if (rawDetail.contains(BIBTEX_PUBLISHER_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_PUBLISHER_PROMPT,"");
                importedBookSection.setJournal(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_VOLUME_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_VOLUME_PROMPT,"");
                importedBookSection.setVolume( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            }  else if (rawDetail.contains(BIBTEX_YEAR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_YEAR_PROMPT,"");
                importedBookSection.setYear( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_PAGES_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_PAGES_PROMPT,"");
                importedBookSection.setPages(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_DOI_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_DOI_PROMPT,"");
                importedBookSection.setDoi(refinedDetail);
            }
        }

        return importedBookSection;
    }

    /**
     * <h2>createPhdThesisFromBibTex</h2>
     * <li>Creates a {@link PhdThesis} object from a BibTex text.</li>
     *
     * @param articleDetails the BibTex text containing article details, already split into an array
     * @return the created {@link PhdThesis} object
     */
    private static ArticleReference createPhdThesisFromBibTex(String[] articleDetails) {
        PhdThesis importedPhdThesis = new PhdThesis();

        for (String rawDetail : articleDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END_PROMPT,"").strip();

            if(rawDetail.contains(BIBTEX_TITLE_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_PROMPT,"");
                importedPhdThesis.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_PROMPT,"").replace(" and ","; ");
                importedPhdThesis.setAuthors(refinedDetail);
            }   else if (rawDetail.contains(BIBTEX_YEAR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_YEAR_PROMPT,"");
                importedPhdThesis.setYear( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_DOI_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_DOI_PROMPT,"");
                importedPhdThesis.setDoi(refinedDetail);
            }
        }

        return importedPhdThesis;
    }

    /**
     * <h2>createPatentFromBibTex</h2>
     * <li>Creates a {@link Patent} object from a BibTex text.</li>
     *
     * @param articleDetails the BibTex text containing article details, already split into an array
     * @return the created {@link Patent} object
     */
    private static ArticleReference createPatentFromBibTex(String[] articleDetails) {
        Patent importedPatent = new Patent();

        for (String rawDetail : articleDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END_PROMPT,"").strip();

            if(rawDetail.contains(BIBTEX_TITLE_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_PROMPT,"");
                importedPatent.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_PROMPT,"").replace(" and ","; ");
                importedPatent.setAuthors(refinedDetail);
            }   else if (rawDetail.contains(BIBTEX_YEAR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_YEAR_PROMPT,"");
                importedPatent.setYear( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_URL_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_URL_PROMPT,"");
                importedPatent.setDoi(refinedDetail);
            }
        }

        return importedPatent;
    }

    /**
     * <h2>createUnpublishedFromBibTex</h2>
     * <li>Creates a {@link Unpublished} object from a BibTex text.</li>
     *
     * @param articleDetails the BibTex text containing article details, already split into an array
     * @return the created {@link Unpublished} object
     */
    private static ArticleReference createUnpublishedFromBibTex(String[] articleDetails) {
        Unpublished importedUnpublished = new Unpublished();

        for (String rawDetail : articleDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END_PROMPT,"").strip();

            if(rawDetail.contains(BIBTEX_TITLE_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_PROMPT,"");
                importedUnpublished.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_PROMPT,"").replace(" and ","; ");
                importedUnpublished.setAuthors(refinedDetail);
            }   else if (rawDetail.contains(BIBTEX_YEAR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_YEAR_PROMPT,"");
                importedUnpublished.setYear( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            }
        }

        return importedUnpublished;
    }
}
