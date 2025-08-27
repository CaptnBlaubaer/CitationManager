package de.apaschold.demo.logic;

import de.apaschold.demo.additionals.MyLittleHelpers;
import de.apaschold.demo.model.Article;
import de.apaschold.demo.model.ArticleType;
import de.apaschold.demo.model.JournalArticle;

public class ArticleFactory {
    //0. constants
    private static final String BIBTEX_TITLE_PROMPT = "title={";
    private static final String BIBTEX_AUTHOR_PROMPT = "author={";
    private static final String BIBTEX_JOURNAL_PROMPT = "journal={";
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
    public static JournalArticle createJournalArticleFromCsvLine(String csvLine){
        String[] parts = csvLine.split(";");

        ArticleType type = ArticleType.valueOf(parts[0]);
        String title = parts[1];
        String authors = parts[2].replace(" and ", "; ");
        String journal = parts[3];
        String journalShortForm = parts[4];
        int year = Integer.parseInt(parts[5]);
        int volume = Integer.parseInt(parts[6]);
        int issue = Integer.parseInt(parts[7]);
        String pages = parts[8];
        String doi = parts[9];
        String url = parts[10];

        return new JournalArticle(type, title, authors, journal, journalShortForm, year, volume, issue, pages, doi, url);
    }

    public static Article createArticleFromBibTex(String bibTexText){
        Article importedArticle = null;

        String[] articleTypeAndDetails = bibTexText.split("\\{", 2);

        ArticleType importedArticleType = ArticleType.getArticleTypeFromBibTexImport(articleTypeAndDetails[0]);

        importedArticle = switch (importedArticleType) {
            case JOURNAL_ARTICLE -> createJournalArticleFromBibTex(articleTypeAndDetails[1]);
            default -> null;
        };

        return importedArticle;
    }

    private static Article createJournalArticleFromBibTex(String articleDetailsAsTextBlock) {
        JournalArticle importedJournalArticle = new JournalArticle();

        importedJournalArticle.setArticleType(ArticleType.JOURNAL_ARTICLE);

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
                importedJournalArticle.setPages(refinedDetail);
            }

        }

        return importedJournalArticle;
    }
}
