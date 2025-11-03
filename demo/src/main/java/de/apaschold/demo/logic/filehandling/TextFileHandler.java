package de.apaschold.demo.logic.filehandling;

import de.apaschold.demo.logic.CitationFactory;
import de.apaschold.demo.model.Citation;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <h2>TextFileHandler</h2>
 * <li>Singleton class that manages reading and writing of text files.</li>
 * <li>Handles import and export of reference libraries in CML format.</li>
 */
public class TextFileHandler {
    //0. constants
    private static final String DEFAULT_LIBRARY_FILE_PATH = "demo/src/main/resources/de/apaschold/demo/data/default-library.cml";
    private static final String ACTIVE_LIBRARY_FILE_PATH = "demo/src/main/resources/de/apaschold/demo/data/active-library.txt";

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
     * <li>Reads a text file and returns its content as a list of {@link Citation}.</li>
     * <li>If there isn't an active library (filePath == null), default library is loaded.</li>
     *
     * @param filePath of the active library as String
     * @return a list of articles resembling the lines of the file
     */
    public List<Citation> importLibraryFromCmlFile(String filePath) {

        if (filePath == null) {
            filePath = DEFAULT_LIBRARY_FILE_PATH;
        }

        List<Citation> citations = new ArrayList<>();

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
                    Citation citationFromCsv = CitationFactory.createCitationFromCsvLine(fileLine);
                    citations.add(citationFromCsv);
                }
            }

        } catch (IOException e){
            System.err.println("Error reading file: " + file.getAbsolutePath());
        }

        return citations;
    }

    /**
     * <h2>exportLibraryToCsv</h2>
     * Writes a list of {@link Citation} to a CSV file.
     *
     * @param citations the list of article information to write to the file
     */
    public void exportLibraryToCml(List<Citation> citations, String activeLibraryFilePath) {

        try (FileWriter writer = new FileWriter(activeLibraryFilePath, StandardCharsets.UTF_8)) {
            for (Citation citation : citations) {
                writer.write(citation.toCsvString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving to File: " + activeLibraryFilePath);
        }
    }

    /**
     * <h2>loadLibraryFilePath</h2>
     * <li>Reads the directory of the active library from active-library.txt</li>
     *
     * @return file path of active library
     */
    public String loadLibraryFilePath(){
        String libraryFilePath = "";

        File file = new File(ACTIVE_LIBRARY_FILE_PATH);

        try(FileReader reader = new FileReader(file);
            BufferedReader in = new BufferedReader(reader)) {

            libraryFilePath = in.readLine();

        } catch (IOException e){
            System.err.println("Error reading file: " + file.getAbsolutePath());
        }

        return libraryFilePath;
    }

    /** <h2>saveNewActiveLibraryPath</h2>
     * <li>Saves the file path of the active library to active-library.txt</li>
     *
     * @param newFilePath the new file path to save
     */
    public void saveNewActiveLibraryPath (String newFilePath, String newFileName){
        try (FileWriter writer = new FileWriter(ACTIVE_LIBRARY_FILE_PATH, StandardCharsets.UTF_8)) {
            writer.write(newFilePath + ";" + newFileName);
        } catch (IOException e) {
            System.err.println("Error saving to File: " );
        }
    }

    /** <h2>exportLibraryToBibTex</h2>
     * Writes a BibTex string to a .bib file.
     *
     * @param libraryAsBibTex the BibTex string representing the article library
     * @param bibTexFilePath  the file path where the BibTex file will be saved
     */

    public void exportLibraryToBibTex(String libraryAsBibTex, String bibTexFilePath){
        try (FileWriter writer = new FileWriter(bibTexFilePath, StandardCharsets.UTF_8)) {
                writer.write( libraryAsBibTex);
        } catch (IOException e) {
            System.err.println("Error saving to File: " + bibTexFilePath);
        }
    }
}
