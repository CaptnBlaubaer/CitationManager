package de.apaschold.demo.logic.filehandling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvHandler {
    //0. constants
    private static final String CSV_SEPARATOR = ";";
    private static final String FILE_PATH = "src/main/resources/de/apaschold/demo/data/articles.csv";

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
    public List<String> readCSVFile() {
        List<String> articlesInList = new ArrayList<>();

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
                    articlesInList.add(fileLine);
                }
            }

        } catch (IOException e){
            System.err.println("Error reading file: " + file.getAbsolutePath());
            e.printStackTrace();
        }

        return articlesInList;
    }

}
