package de.apaschold.demo.logic;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.model.*;

/**
 * <h2>CitationFactory</h2>
 * <p>Factory class for creating {@link Citation} objects from different input formats.</p>
 */

public class CitationFactory {
    //0. constants
    private static final String BIBTEX_TITLE_KEY = "title={";
    private static final String BIBTEX_AUTHOR_KEY = "author={";
    private static final String BIBTEX_BOOK_TITLE_KEY = "booktitle={";
    private static final String BIBTEX_EDITOR_KEY = "editor={";
    private static final String BIBTEX_JOURNAL_KEY = "journal={";
    private static final String BIBTEX_PUBLISHER_KEY = "publisher={";
    private static final String BIBTEX_VOLUME_KEY = "volume={";
    private static final String BIBTEX_ISSUE_KEY = "number={";
    private static final String BIBTEX_PAGES_KEY = "pages={";
    private static final String BIBTEX_YEAR_KEY = "year={";
    private static final String BIBTEX_DOI_KEY = "DOI={";
    private static final String BIBTEX_URL_KEY = "url={";
    private static final String BIBTEX_LINE_END = "},";
    private static final String BIBTEX_LAST_ITEM_END = "}";

    //1. attributes

    //2. constructors
    private CitationFactory(){}

    //3. factory methods
    //methods for csv
    /**
     * <h2>createCitationFromCsvLine</h2>
     * <li>Creates an {@link Citation} object from a CSV line.</li>
     * <li>Chooses dynamically method for the respective {@link CitationType}</li>
     *
     * @param csvLine the CSV line containing citation details
     * @return the created {@link Citation} object
     */
    public static Citation createCitationFromCsvLine(String csvLine){
        String[] separatedCsvLine = csvLine.split(";");

        CitationType type = CitationType.valueOf(separatedCsvLine[1]);

        Citation newCitation = switch (type) {
            case JOURNAL_ARTICLE -> createJournalArticleFromCsvLine(separatedCsvLine);
            case BOOK -> createBookFromCsvLine(separatedCsvLine);
            case BOOK_SECTION ->  createBookSectionFromCsvLine(separatedCsvLine);
            case THESIS -> createPhdThesisFromCsvLine(separatedCsvLine);
            case PATENT -> createPatentFromCsvLine(separatedCsvLine);
            case UNPUBLISHED -> createUnpublishedFromCsvLine(separatedCsvLine);
            default -> null;
        };

        return newCitation;
    }

    /**
     * <h2>createJournalArticleFromCsvLine</h2>
     * <li>Creates a {@link JournalArticle} object from a CSV line.</li>
     *
     * @param separatedCsvLine the CSV line containing citation details, already split into an array
     * @return the created {@link JournalArticle} object
     */
    private static JournalArticle createJournalArticleFromCsvLine(String[] separatedCsvLine){
        int id = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[0]);
        String title = separatedCsvLine[2];
        String authors = separatedCsvLine[3].replace(" and ", "; ");
        String journal = separatedCsvLine[4];
        int year = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[5]);
        String doi = separatedCsvLine[6];
        String pdfFilePath = separatedCsvLine[7];
        String journalShortForm = separatedCsvLine[8];
        int volume = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[9]);
        int issue = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[10]);
        String pages = separatedCsvLine[11];

        return new JournalArticle(id, title, authors, journal, journalShortForm, year, volume, issue, pages, doi, pdfFilePath);
    }

    /**
     * <h2>createBookFromCsvLine</h2>
     * <li>Creates a {@link Book} object from a CSV line.</li>
     *
     * @param separatedCsvLine the CSV line containing citation details, already split into an array
     * @return the created {@link Book} object
     */
    private static Book createBookFromCsvLine(String[] separatedCsvLine){
        int id = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[0]);
        String title = separatedCsvLine[2];
        String authors = separatedCsvLine[3].replace(" and ", "; ");
        String journal = separatedCsvLine[4];
        int year = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[5]);
        String doi = separatedCsvLine[6];
        String pdfFilePath = separatedCsvLine[7];
        int volume = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[9]);
        return new Book(id, title, authors, journal, year, volume, doi, pdfFilePath);
    }

    /**
     * <h2>createBookSectionFromCsvLine</h2>
     * <li>Creates a {@link BookSection} object from a CSV line.</li>
     *
     * @param separatedCsvLine the CSV line containing citation details, already split into an array
     * @return the created {@link BookSection} object
     */
    private static BookSection createBookSectionFromCsvLine(String[] separatedCsvLine){
        int id = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[0]);
        String title = separatedCsvLine[2];
        String authors = separatedCsvLine[3].replace(" and ", "; ");
        String journal = separatedCsvLine[4];
        int year = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[5]);
        String doi = separatedCsvLine[6];
        String pdfFilePath = separatedCsvLine[7];
        int volume = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[9]);
        String pages = separatedCsvLine[11];
        String bookTitle = separatedCsvLine[12];
        String editor = separatedCsvLine[13].replace(" and ", "; ");

        return new BookSection(id, title, authors, bookTitle, editor, journal, year, volume, pages, doi, pdfFilePath);
    }

    /**
     * <h2>createPhdThesisFromCsvLine</h2>
     * <li>Creates a {@link PhdThesis} object from a CSV line.</li>
     *
     * @param separatedCsvLine the CSV line containing citation details, already split into an array
     * @return the created {@link PhdThesis} object
     */
    private static Citation createPhdThesisFromCsvLine(String[] separatedCsvLine) {
        int id = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[0]);
        String title = separatedCsvLine[2];
        String authors = separatedCsvLine[3].replace(" and ", "; ");
        int year = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[5]);
        String doi = separatedCsvLine[6];
        String pdfFilePath = separatedCsvLine[7];
        return new PhdThesis(id, title, authors, year, doi, pdfFilePath);
    }

    /**
     * <h2>createPatentFromCsvLine</h2>
     * <li>Creates a {@link Patent} object from a CSV line.</li>
     *
     * @param separatedCsvLine the CSV line containing citation details, already split into an array
     * @return the created {@link Patent} object
     */
    private static Citation createPatentFromCsvLine(String[] separatedCsvLine) {
        int id = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[0]);
        String title = separatedCsvLine[2];
        String authors = separatedCsvLine[3].replace(" and ", "; ");
        int year = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[5]);
        String doi = separatedCsvLine[6];
        String pdfFilePath = separatedCsvLine[7];
        return new Patent(id, title, authors, year, doi, pdfFilePath);
    }

    /**
     * <h2>createUnpublishedFromCsvLine</h2>
     * <li>Creates a {@link Unpublished} object from a CSV line.</li>
     *
     * @param separatedCsvLine the CSV line containing citation details, already split into an array
     * @return the created {@link Unpublished} object
     */
    private static Citation createUnpublishedFromCsvLine(String[] separatedCsvLine) {
        int id = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[0]);
        String title = separatedCsvLine[2];
        String authors = separatedCsvLine[3].replace(" and ", "; ");
        int year = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[5]);
        String pdfFilePath = separatedCsvLine[7];
        return new Unpublished (id, title, authors, year, pdfFilePath);
    }

    //methods for BibTex
    /**
     * <h2>createCitationFromBibTex</h2>
     * <li>Creates an {@link Citation} object from a BibTex text.</li>
     * <li>Chooses dynamically method for the respective {@link CitationType}</li>
     *
     * @param bibTexText the BibTex text containing citaion details
     * @return the created {@link Citation} object
     */
    public static Citation createCitationFromBibTex(String bibTexText){
        String[] citationTypeAndDetails = bibTexText.split("\\{", 2); //

        CitationType importedCitationType = CitationType.getCitationTypeFromBibTexImport(citationTypeAndDetails[0]);

        String[] citationDetails = citationTypeAndDetails[1].split("\n");

        return switch (importedCitationType) {
            case JOURNAL_ARTICLE -> createJournalArticleFromBibTex(citationDetails);
            case BOOK -> createBookFromBibTex(citationDetails);
            case BOOK_SECTION -> createBookSectionFromBibTex(citationDetails);
            case THESIS -> createPhdThesisFromBibTex(citationDetails);
            case PATENT -> createPatentFromBibTex(citationDetails);
            case UNPUBLISHED -> createUnpublishedFromBibTex(citationDetails);
            default -> null;
        };
    }

    /**
     * <h2>createJournalArticleFromBibTex</h2>
     * <li>Creates a {@link JournalArticle} object from a BibTex text.</li>
     *
     * @param citationDetails the BibTex text containing citation details, already split into an array
     * @return the created {@link JournalArticle} object
     */
    private static Citation createJournalArticleFromBibTex(String[] citationDetails) {
        JournalArticle importedJournalArticle = new JournalArticle();

        for (String rawDetail : citationDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END,"")
                    .replace(BIBTEX_LAST_ITEM_END, "")
                    .strip();

            if(rawDetail.contains(BIBTEX_TITLE_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_KEY,"");
                importedJournalArticle.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_KEY,"").replace(" and ","; ");
                importedJournalArticle.setAuthors(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_JOURNAL_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_JOURNAL_KEY,"");
                importedJournalArticle.setJournal(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_VOLUME_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_VOLUME_KEY,"");
                importedJournalArticle.setVolume( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_ISSUE_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_ISSUE_KEY,"");
                importedJournalArticle.setIssue( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_YEAR_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_YEAR_KEY,"");
                importedJournalArticle.setYear( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_PAGES_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_PAGES_KEY,"");
                importedJournalArticle.setPages(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_DOI_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_DOI_KEY,"");
                importedJournalArticle.setDoi(refinedDetail);
            }
        }

        return importedJournalArticle;
    }

    /**
     * <h2>createBookFromBibTex</h2>
     * <li>Creates a {@link Book} object from a BibTex text.</li>
     *
     * @param citationDetails the BibTex text containing citation details, already split into an array
     * @return the created {@link Book} object
     */
    private static Citation createBookFromBibTex(String[] citationDetails) {
        Book importedBook = new Book();

        for (String rawDetail : citationDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END,"")
                    .replace(BIBTEX_LAST_ITEM_END, "")
                    .strip();

            if(rawDetail.contains(BIBTEX_TITLE_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_KEY,"");
                importedBook.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_KEY,"").replace(" and ","; ");
                importedBook.setAuthors(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_PUBLISHER_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_PUBLISHER_KEY,"");
                importedBook.setJournal(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_VOLUME_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_VOLUME_KEY,"");
                importedBook.setVolume( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_YEAR_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_YEAR_KEY,"");
                importedBook.setYear( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_DOI_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_DOI_KEY,"");
                importedBook.setDoi(refinedDetail);
            }
        }

        return importedBook;
    }

    /**
     * <h2>createBookSectionFromBibTex</h2>
     * <li>Creates a {@link BookSection} object from a BibTex text.</li>
     *
     * @param citationDetails the BibTex text containing citation details, already split into an array
     * @return the created {@link BookSection} object
     */
    private static Citation createBookSectionFromBibTex(String[] citationDetails) {
        BookSection importedBookSection = new BookSection();

        for (String rawDetail : citationDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END,"")
                    .replace(BIBTEX_LAST_ITEM_END, "")
                    .strip();

            if(rawDetail.contains(BIBTEX_BOOK_TITLE_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_BOOK_TITLE_KEY,"");
                importedBookSection.setBookTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_KEY,"").replace(" and ","; ");
                importedBookSection.setAuthors(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_TITLE_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_KEY,"");
                importedBookSection.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_EDITOR_KEY)) {
                refinedDetail = refinedDetail.replace(BIBTEX_EDITOR_KEY, "").replace(" and ", "; ");
                importedBookSection.setEditor(refinedDetail);
            }else if (rawDetail.contains(BIBTEX_PUBLISHER_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_PUBLISHER_KEY,"");
                importedBookSection.setJournal(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_VOLUME_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_VOLUME_KEY,"");
                importedBookSection.setVolume( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            }  else if (rawDetail.contains(BIBTEX_YEAR_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_YEAR_KEY,"");
                importedBookSection.setYear( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_PAGES_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_PAGES_KEY,"");
                importedBookSection.setPages(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_DOI_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_DOI_KEY,"");
                importedBookSection.setDoi(refinedDetail);
            }
        }

        return importedBookSection;
    }

    /**
     * <h2>createPhdThesisFromBibTex</h2>
     * <li>Creates a {@link PhdThesis} object from a BibTex text.</li>
     *
     * @param citationDetails the BibTex text containing citation details, already split into an array
     * @return the created {@link PhdThesis} object
     */
    private static Citation createPhdThesisFromBibTex(String[] citationDetails) {
        PhdThesis importedPhdThesis = new PhdThesis();

        for (String rawDetail : citationDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END,"")
                    .replace(BIBTEX_LAST_ITEM_END, "")
                    .strip();

            if(rawDetail.contains(BIBTEX_TITLE_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_KEY,"");
                importedPhdThesis.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_KEY,"").replace(" and ","; ");
                importedPhdThesis.setAuthors(refinedDetail);
            }   else if (rawDetail.contains(BIBTEX_YEAR_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_YEAR_KEY,"");
                importedPhdThesis.setYear( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_DOI_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_DOI_KEY,"");
                importedPhdThesis.setDoi(refinedDetail);
            }
        }

        return importedPhdThesis;
    }

    /**
     * <h2>createPatentFromBibTex</h2>
     * <li>Creates a {@link Patent} object from a BibTex text.</li>
     *
     * @param citationDetails the BibTex text containing citation details, already split into an array
     * @return the created {@link Patent} object
     */
    private static Citation createPatentFromBibTex(String[] citationDetails) {
        Patent importedPatent = new Patent();

        for (String rawDetail : citationDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END,"")
                    .replace(BIBTEX_LAST_ITEM_END, "")
                    .strip();

            if(rawDetail.contains(BIBTEX_TITLE_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_KEY,"");
                importedPatent.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_KEY,"").replace(" and ","; ");
                importedPatent.setAuthors(refinedDetail);
            }   else if (rawDetail.contains(BIBTEX_YEAR_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_YEAR_KEY,"");
                importedPatent.setYear( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            } else if (rawDetail.contains(BIBTEX_URL_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_URL_KEY,"");
                importedPatent.setDoi(refinedDetail);
            }
        }

        return importedPatent;
    }

    /**
     * <h2>createUnpublishedFromBibTex</h2>
     * <li>Creates a {@link Unpublished} object from a BibTex text.</li>
     *
     * @param citationDetails the BibTex text containing citation details, already split into an array
     * @return the created {@link Unpublished} object
     */
    private static Citation createUnpublishedFromBibTex(String[] citationDetails) {
        Unpublished importedUnpublished = new Unpublished();

        for (String rawDetail : citationDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END,"")
                    .replace(BIBTEX_LAST_ITEM_END, "")
                    .strip();

            if(rawDetail.contains(BIBTEX_TITLE_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_KEY,"");
                importedUnpublished.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_KEY,"").replace(" and ","; ");
                importedUnpublished.setAuthors(refinedDetail);
            }   else if (rawDetail.contains(BIBTEX_YEAR_KEY)){
                refinedDetail = refinedDetail.replace(BIBTEX_YEAR_KEY,"");
                importedUnpublished.setYear( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            }
        }

        return importedUnpublished;
    }

    /**
     * <h2>createCitationFromManualDataInput</h2>
     * <li>Creates an {@link Citation} object from a manually entered data string.</li>
     * <li>Chooses dynamically method for the respective {@link CitationType}</li>
     *
     * @param citationDetailsAsString the string containing citation details separated by semicolons
     * @return the created {@link Citation} object
     */
    public static Citation createCitationFromManualDataInput(String citationDetailsAsString) {
        String[] citationDetailsAsArray = citationDetailsAsString.split(";");

        CitationType citationType = CitationType.valueOf(citationDetailsAsArray[0]);

        return switch (citationType) {
            case JOURNAL_ARTICLE -> createJournalArticleFromManualDataInput(citationDetailsAsArray);
            case BOOK -> createBookFromManualDataInput(citationDetailsAsArray);
            case BOOK_SECTION -> createBookSectionFromManualDataInput(citationDetailsAsArray);
            case THESIS -> createPhdThesisFromManualDataInput(citationDetailsAsArray);
            case PATENT -> createPatentFromManualDataInput(citationDetailsAsArray);
            case UNPUBLISHED -> createUnpublishedFromManualDataInput(citationDetailsAsArray);
            default -> null;
        };
    }

    /**
     * <h2>createJournalArticleFromManualDataInput</h2>
     * <li>Creates a {@link JournalArticle} object from a manually entered data string.</li>
     *
     * @param citationDetails the array containing citation details
     * @return the created {@link JournalArticle} object
     */
    private static Citation createJournalArticleFromManualDataInput(String[] citationDetails) {
        return new JournalArticle(
                -1,
                citationDetails[1], //title
                citationDetails[2], //authors
                citationDetails[3], //journal
                citationDetails[4], //journalShortForm
                MyLittleHelpers.convertStringInputToInteger(citationDetails[5]), //year
                MyLittleHelpers.convertStringInputToInteger(citationDetails[6]), //volume
                MyLittleHelpers.convertStringInputToInteger(citationDetails[7]), //issue
                citationDetails[8], //pages
                citationDetails[9], //doi
                AppTexts.PLACEHOLDER
        );
    }

    /**
     * <h2>createBookFromManualDataInput</h2>
     * <li>Creates a {@link Book} object from a manually entered data string.</li>
     *
     * @param citationDetails the array containing citation details
     * @return the created {@link Book} object
     */
    private static Citation createBookFromManualDataInput(String[] citationDetails) {
        return new Book(
                -1,
                citationDetails[1], //title
                citationDetails[2], //authors
                citationDetails[3], //publisher
                MyLittleHelpers.convertStringInputToInteger(citationDetails[4]), //year
                MyLittleHelpers.convertStringInputToInteger(citationDetails[5]), //volume
                citationDetails[6], //doi
                AppTexts.PLACEHOLDER
        );
    }

    /**
     * <h2>createBookSectionFromManualDataInput</h2>
     * <li>Creates a {@link BookSection} object from a manually entered data string.</li>
     *
     * @param citationDetails the array containing citation details
     * @return the created {@link BookSection} object
     */
    private static Citation createBookSectionFromManualDataInput(String[] citationDetails) {
        return new BookSection(
                -1,
                citationDetails[1], //title
                citationDetails[2], //authors
                citationDetails[3], //bookTitle
                citationDetails[4], //editor
                citationDetails[5], //publisher
                MyLittleHelpers.convertStringInputToInteger(citationDetails[6]), //year
                MyLittleHelpers.convertStringInputToInteger(citationDetails[7]), //volume
                citationDetails[8], //pages
                citationDetails[9], //doi
                AppTexts.PLACEHOLDER
        );
    }

    /**
     * <h2>createPhdThesisFromManualDataInput</h2>
     * <li>Creates a {@link PhdThesis} object from a manually entered data string.</li>
     *
     * @param citationDetails the array containing citation details
     * @return the created {@link PhdThesis} object
     */
    private static Citation createPhdThesisFromManualDataInput(String[] citationDetails) {
        return new PhdThesis(
                -1,
                citationDetails[1], //title
                citationDetails[2], //authors
                MyLittleHelpers.convertStringInputToInteger(citationDetails[3]), //year
                citationDetails[4], //doi
                AppTexts.PLACEHOLDER
        );
    }

    /**
     * <h2>createPatentFromManualDataInput</h2>
     * <li>Creates a {@link Patent} object from a manually entered data string.</li>
     *
     * @param citationDetails the array containing citation details
     * @return the created {@link Patent} object
     */
    private static Citation createPatentFromManualDataInput(String[] citationDetails) {
        return new Patent(
                -1,
                citationDetails[1], //title
                citationDetails[2], //authors
                MyLittleHelpers.convertStringInputToInteger(citationDetails[3]), //year
                citationDetails[4], //doi
                AppTexts.PLACEHOLDER
        );
    }

    /**
     * <h2>createUnpublishedFromManualDataInput</h2>
     * <li>Creates a {@link Unpublished} object from a manually entered data string.</li>
     *
     * @param citationDetails the array containing citation details
     * @return the created {@link Unpublished} object
     */
    private static Citation createUnpublishedFromManualDataInput(String[] citationDetails) {
        return new Unpublished(
                -1,
                citationDetails[1], //title
                citationDetails[2], //authors
                MyLittleHelpers.convertStringInputToInteger(citationDetails[3]), //year
                AppTexts.PLACEHOLDER
        );
    }
}
