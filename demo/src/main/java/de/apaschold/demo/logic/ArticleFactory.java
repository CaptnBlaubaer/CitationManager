package de.apaschold.demo.logic;

import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.model.Article;
import de.apaschold.demo.model.ArticleType;
import de.apaschold.demo.model.Book;
import de.apaschold.demo.model.JournalArticle;

//TODO Create methods for book, book section, phd thesis

public class ArticleFactory {
    //0. constants
    private static final String BIBTEX_TITLE_PROMPT = "title={";
    private static final String BIBTEX_AUTHOR_PROMPT = "author={";
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

    public static Article createArticleFromBibTex(String bibTexText){
        Article importedArticle = null;

        String[] articleTypeAndDetails = bibTexText.split("\\{", 2);

        ArticleType importedArticleType = ArticleType.getArticleTypeFromBibTexImport(articleTypeAndDetails[0]);

        importedArticle = switch (importedArticleType) {
            case JOURNAL_ARTICLE -> createJournalArticleFromBibTex(articleTypeAndDetails[1]);
            case BOOK -> createBookFromBibTex(articleTypeAndDetails[1]);
            default -> null;
        };

        return importedArticle;
    }

    private static Article createJournalArticleFromBibTex(String articleDetailsAsTextBlock) {
        JournalArticle importedJournalArticle = new JournalArticle();

        String[] articleDetails = articleDetailsAsTextBlock.split("\n");

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

    private static Article createBookFromBibTex(String articleDetailsAsTextBlock) {
        Book importedBook = new Book();

        importedBook.setArticleType(ArticleType.BOOK);

        String[] articleDetails = articleDetailsAsTextBlock.split("\n");

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
}
