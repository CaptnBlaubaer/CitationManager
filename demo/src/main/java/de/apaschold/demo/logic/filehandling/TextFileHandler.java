package de.apaschold.demo.logic.filehandling;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * <h2>TextFileHandler</h2>
 * <li>Singleton class that manages reading and writing of text files.</li>
 * <li>Handles import and export of reference libraries in CML format.</li>
 */
public class TextFileHandler {
    //0. constants
    private static final String EXAMPLE_LIBRARY_PATH = "demo/src/main/resources/de/apaschold/demo/data/testLibraryPaschold.db";

    //1. attributes
    private static TextFileHandler instance;
    private static String activeLibraryFilePath = "demo/src/main/resources/de/apaschold/demo/data/active-library.txt";

    //2. constructors
    private TextFileHandler() {
    }

    public static synchronized TextFileHandler getInstance() {
        if (instance == null) {
            instance = new TextFileHandler();
        }
        return instance;
    }

    //3. getter and setter methods
    public static void setActiveLibraryFilePath(String newPath) {
        activeLibraryFilePath = newPath;
    }

    //4. read'n'write methods
    /**
     * <h2>loadLibraryFilePath</h2>
     * <li>Reads the directory of the active library from active-library.txt</li>
     *
     * @return file path of active library
     */
    public String loadLibraryFilePath(){
        String libraryFilePath = "";

        File file = new File(activeLibraryFilePath);

        try(FileReader reader = new FileReader(file);
            BufferedReader in = new BufferedReader(reader)) {

            libraryFilePath = in.readLine();

        } catch (IOException e){
            System.err.println("Error reading file: " + file.getAbsolutePath());
        }

        if (!new File(libraryFilePath).exists()){
            libraryFilePath = EXAMPLE_LIBRARY_PATH;
        }

        return libraryFilePath;
    }

    /** <h2>saveNewActiveLibraryPath</h2>
     * <li>Saves the file path of the active library to active-library.txt</li>
     *
     * @param newFilePath the new file path to save
     */
    public void saveNewActiveLibraryPath (String newFilePath){
        try (FileWriter writer = new FileWriter(activeLibraryFilePath, StandardCharsets.UTF_8)) {
            writer.write(newFilePath);
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
