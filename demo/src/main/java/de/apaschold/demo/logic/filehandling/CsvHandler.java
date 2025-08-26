package de.apaschold.demo.logic.filehandling;

import de.apaschold.demo.model.Article;
import de.apaschold.demo.model.JournalArticle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvHandler {
    //0. constants
    private static final String CSV_SEPARATOR = ";";
    private static final String FILE_PATH = "demo/src/main/resources/de/apaschold/demo/data/articles.csv";

    //1. attributes
    private static CsvHandler instance;

    //2. constructors
    private CsvHandler() {
    }

    public static synchronized CsvHandler getInstance() {
        if (instance == null) {
            instance = new CsvHandler();
        }
        return instance;
    }

    //3. read'n'write methods
    /**
     * <h2>readCSVFile</h2>
     * Reads a text file and returns its content as a list of strings, where each string is a line from the file.
     *
     * @return a list of strings containing the lines of the file
     */
    public List<Article> readArticleInfosCsvFile() {
        List<Article> articlesInList = new ArrayList<>();

        File file = new File(FILE_PATH);

        try(FileReader reader = new FileReader(file);
            BufferedReader in = new BufferedReader(reader)) {

            String fileLine;
            boolean eof = false;

            while (!eof) {
                fileLine = in.readLine();
                if (fileLine == null) {
                    eof = true;
                } else {
                    JournalArticle journalArticle = ArticleFactory.createArticleFromCsvLine(fileLine);
                    articlesInList.add(journalArticle);

                }
            }

        } catch (IOException e){
            System.err.println("Error reading file: " + file.getAbsolutePath());
            e.printStackTrace();
        }

        return articlesInList;
    }

    /**
     * <h2>writeArticleInfosInCsv</h2>
     * Writes a list of article information to a CSV file.
     *
     * @param articlesInList the list of article information to write to the file
     */
    public void writeArticleInfosInCsv(List<String> articlesInList) {

        try (FileWriter writer = new FileWriter(FILE_PATH, StandardCharsets.UTF_8)) {
            for (String articles : articlesInList) {
                writer.write(articles + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving to File: " + FILE_PATH);
            e.printStackTrace();
        }
    }

}
