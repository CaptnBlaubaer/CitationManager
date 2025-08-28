package de.apaschold.demo.logic.filehandling;

import de.apaschold.demo.logic.ArticleFactory;
import de.apaschold.demo.model.ArticleReference;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TextFileHandler {
    //0. constants
    private static final String DEFAULT_LIBRARY_FILE_PATH = "demo/src/main/resources/de/apaschold/demo/data/default-library.cml";
    private static final String LOAD_LIBRARY_FILE_PATH = "demo/src/main/resources/de/apaschold/demo/data/active-library.txt";

    //1. attributes
    private static TextFileHandler instance;

    //2. constructors
    private TextFileHandler() {
    }

    public static synchronized TextFileHandler getInstance() {
        if (instance == null) {
            instance = new TextFileHandler();
        }
        return instance;
    }

    //3. read'n'write methods
    /**
     * <h2>importLibraryFromCsvFile</h2>
     * <li>Reads a text file and returns its content as a list of {@link ArticleReference}.</li>
     * <li>If there isn't an active library (filePath == null), default library is loaded.</li>
     *
     * @param filePath of the active library as String
     * @return a list of articles resembling the lines of the file
     */
    public List<ArticleReference> importLibraryFromCsvFile(String filePath) {

        if (filePath == null) {
            filePath = DEFAULT_LIBRARY_FILE_PATH;
        }

        List<ArticleReference> articleReferences = new ArrayList<>();

        File file = new File(filePath);

        try(FileReader reader = new FileReader(file);
            BufferedReader in = new BufferedReader(reader)) {

            String fileLine;
            boolean eof = false;

            while (!eof) {
                fileLine = in.readLine();
                if (fileLine == null) {
                    eof = true;
                } else {
                    ArticleReference articleReferenceFromCsv = ArticleFactory.createArticleReferenceFromCsvLine(fileLine);
                    articleReferences.add(articleReferenceFromCsv);
                }
            }

        } catch (IOException e){
            System.err.println("Error reading file: " + file.getAbsolutePath());
        }

        return articleReferences;
    }

    /**
     * <h2>exportLibraryToCsv</h2>
     * Writes a list of {@link ArticleReference} to a CSV file.
     *
     * @param articleReferences the list of article information to write to the file
     */
    public void exportLibraryToCsv(List<ArticleReference> articleReferences, String activeLibraryFilePath) {

        try (FileWriter writer = new FileWriter(activeLibraryFilePath, StandardCharsets.UTF_8)) {
            for (ArticleReference articleReference : articleReferences) {
                writer.write(articleReference.toCsvString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving to File: " + activeLibraryFilePath);
        }
    }

    public String loadLibraryFilePath(){
        String filePath = LOAD_LIBRARY_FILE_PATH;

        String libraryFilePath = "";

        File file = new File(filePath);

        try(FileReader reader = new FileReader(file);
            BufferedReader in = new BufferedReader(reader)) {

            libraryFilePath = in.readLine();

        } catch (IOException e){
            System.err.println("Error reading file: " + file.getAbsolutePath());
        }

        return libraryFilePath;
    }

}
