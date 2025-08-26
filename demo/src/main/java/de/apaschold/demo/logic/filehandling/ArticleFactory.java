package de.apaschold.demo.logic.filehandling;

import de.apaschold.demo.model.ArticleType;
import de.apaschold.demo.model.JournalArticle;

import java.util.Arrays;

public class ArticleFactory {

    public static JournalArticle createArticleFromCsvLine(String csvLine){
        String[] parts = csvLine.split(";");

        ArticleType type = ArticleType.valueOf(parts[0]);
        String title = parts[1];
        String authors = parts[2].replace("@", ", ");
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
}
