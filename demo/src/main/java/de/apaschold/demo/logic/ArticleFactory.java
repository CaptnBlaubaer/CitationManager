package de.apaschold.demo.logic;

import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.model.*;

//TODO Create methods for book, book section, phd thesis
//TODO rename booksection variable (importedJournalArticle)


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
    private static final String BIBTEX_LINE_END_PROMPT = "},";

    //1. attributes

    //2. constructors
    private ArticleFactory(){}

    //3. factory methods
    public static Article createArticleFromCsvLine(String csvLine){
        String[] separatedCsvLine = csvLine.split(";");

        ArticleType type = ArticleType.valueOf(separatedCsvLine[0]);

        Article newArticle = switch (type) {
            case JOURNAL_ARTICLE -> createJournalArticleFromCsvLine(separatedCsvLine);
            case BOOK -> createBookFromCsvLine(separatedCsvLine);
            case BOOK_SECTION ->  createBookSectionFromCsvLine(separatedCsvLine);
            case THESIS -> createPhdThesisFromCsvLine(separatedCsvLine);
            default -> null;
        };

        return newArticle;
    }

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
        String url = separatedCsvLine[10];

        return new JournalArticle(title, authors, journal, journalShortForm, year, volume, issue, pages, doi, url);
    }

    private static Book createBookFromCsvLine(String[] separatedCsvLine){
        String title = separatedCsvLine[1];
        String authors = separatedCsvLine[2].replace(" and ", "; ");
        String journal = separatedCsvLine[3];
        int year = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[4]);
        int volume = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[5]);
        String doi = separatedCsvLine[6];
        String url = separatedCsvLine[7];
        return new Book(title, authors, journal, year, volume, doi, url);
    }

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
        String url = separatedCsvLine[10];

        return new BookSection(title, authors, bookTitle, editor, journal, year, volume, pages, doi, url);
    }

    private static Article createPhdThesisFromCsvLine(String[] separatedCsvLine) {
        String title = separatedCsvLine[1];
        String authors = separatedCsvLine[2].replace(" and ", "; ");
        int year = MyLittleHelpers.convertStringInputToInteger(separatedCsvLine[3]);
        String doi = separatedCsvLine[4];
        String url = separatedCsvLine[5];
        return new PhdThesis(title, authors, year, doi, url);
    }

    public static Article createArticleFromBibTex(String bibTexText){
        String[] articleTypeAndDetails = bibTexText.split("\\{", 2); //

        ArticleType importedArticleType = ArticleType.getArticleTypeFromBibTexImport(articleTypeAndDetails[0]);

        String[] articleDetails = articleTypeAndDetails[1].split("\n");

        Article importedArticle = switch (importedArticleType) {
            case JOURNAL_ARTICLE -> createJournalArticleFromBibTex(articleDetails);
            case BOOK -> createBookFromBibTex(articleDetails);
            case BOOK_SECTION -> createBookSectionFromBibTex(articleDetails);
            case THESIS -> createPhdThesisFromBibTex(articleDetails);
            default -> null;
        };

        return importedArticle;
    }

    private static Article createJournalArticleFromBibTex(String[] articleDetails) {
        JournalArticle importedJournalArticle = new JournalArticle();

        for (String rawDetail : articleDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END_PROMPT,"").strip();

            if(rawDetail.contains(BIBTEX_TITLE_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_PROMPT,"");
                importedJournalArticle.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_PROMPT,"").replace(" and ","; ");
                importedJournalArticle.setAuthor(refinedDetail);
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

    private static Article createBookFromBibTex(String[] articleDetails) {
        Book importedBook = new Book();

        for (String rawDetail : articleDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END_PROMPT,"").strip();

            if(rawDetail.contains(BIBTEX_TITLE_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_PROMPT,"");
                importedBook.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_PROMPT,"").replace(" and ","; ");
                importedBook.setAuthor(refinedDetail);
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

    private static Article createBookSectionFromBibTex(String[] articleDetails) {
        BookSection importedJournalArticle = new BookSection();

        for (String rawDetail : articleDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END_PROMPT,"").strip();

            if(rawDetail.contains(BIBTEX_BOOK_TITLE_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_BOOK_TITLE_PROMPT,"");
                importedJournalArticle.setBookTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_PROMPT,"").replace(" and ","; ");
                importedJournalArticle.setAuthor(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_TITLE_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_PROMPT,"");
                importedJournalArticle.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_EDITOR_PROMPT)) {
                refinedDetail = refinedDetail.replace(BIBTEX_EDITOR_PROMPT, "").replace(" and ", "; ");
                importedJournalArticle.setEditor(refinedDetail);
            }else if (rawDetail.contains(BIBTEX_PUBLISHER_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_PUBLISHER_PROMPT,"");
                importedJournalArticle.setJournal(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_VOLUME_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_VOLUME_PROMPT,"");
                importedJournalArticle.setVolume( MyLittleHelpers.convertStringInputToInteger(refinedDetail));
            }  else if (rawDetail.contains(BIBTEX_YEAR_PROMPT)){
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


    private static Article createPhdThesisFromBibTex(String[] articleDetails) {
        PhdThesis importedPhdThesis = new PhdThesis();

        for (String rawDetail : articleDetails){
            String refinedDetail = rawDetail.replace(BIBTEX_LINE_END_PROMPT,"").strip();

            if(rawDetail.contains(BIBTEX_TITLE_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_TITLE_PROMPT,"");
                importedPhdThesis.setTitle(refinedDetail);
            } else if (rawDetail.contains(BIBTEX_AUTHOR_PROMPT)){
                refinedDetail = refinedDetail.replace(BIBTEX_AUTHOR_PROMPT,"").replace(" and ","; ");
                importedPhdThesis.setAuthor(refinedDetail);
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
}
