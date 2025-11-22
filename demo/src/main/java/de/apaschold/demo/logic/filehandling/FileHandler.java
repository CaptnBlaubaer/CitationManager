package de.apaschold.demo.logic.filehandling;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.logic.CitationService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * <h2>FileHandler</h2>
 * <li>Singleton class that manages reading and writing of files.</li>
 * <li>Handles creation of new library files and management of PDF attachments.</li>
 */

public class FileHandler {
    //0. constants

    //1. attributes
    private static FileHandler instance;

    //2. constructors
    private FileHandler() {
    }

    public static synchronized FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }

    //3. read'n'write methods
    /**
     * <h2>createEmptyLibrary</h2>
     * <li>Creates an empty library file and its corresponding PDF folder.</li>
     *
     * @param newLibraryFile the File object representing the new library file to be created
     */

    public void createEmptyLibrary(File newLibraryFile){
        String pdfDirectoryPath = newLibraryFile.getAbsolutePath().replace(AppTexts.LIBRARY_FILE_FORMAT, AppTexts.PDF_FOLDER_EXTENSION);

        try (FileWriter writer = new FileWriter(newLibraryFile , StandardCharsets.UTF_8)) {
            writer.write("");

            File theDir = new File(pdfDirectoryPath);
            if (!theDir.exists()){
                theDir.mkdirs();
            }
        } catch (IOException e) {
            System.err.println("Error saving to File: " + pdfDirectoryPath);
        }
    }

    /**
     * <h2>deleteSelectedAttachmentFromFolder</h2>
     * <li>Deletes the specified attachment file from the PDF folder of the active library.</li>
     *
     * @param chosenAttachment the name of the attachment file to be deleted
     */
    public void deleteSelectedAttachmentFromFolder(String chosenAttachment) {
        String activeLibraryFilePath = CitationService.getActiveLibraryFilePath();
        String pdfFolderPath = activeLibraryFilePath.replace(AppTexts.LIBRARY_FILE_FORMAT,AppTexts.PDF_FOLDER_EXTENSION);

        File attachmentToDelete = new File(pdfFolderPath + chosenAttachment);
        if (!attachmentToDelete.isDirectory()) {
            attachmentToDelete.delete();
        }
    }

    /**
     * <h2>copySelectedAttachmentToPdfFolder</h2>
     * <li>Copies the specified attachment file to the PDF folder of the active library.</li>
     *
     * @param chosenFile the File object representing the attachment file to be copied
     */

    public void copySelectedAttachmentToPdfFolder(File chosenFile) {
        String activeLibraryFilePath = CitationService.getActiveLibraryFilePath();
        String pdfFolderPath = activeLibraryFilePath.replace(AppTexts.LIBRARY_FILE_FORMAT,AppTexts.PDF_FOLDER_EXTENSION);

        String chosenFileName = chosenFile.getName();
        String destinationPath = pdfFolderPath + chosenFileName;

        try {
            Files.copy(chosenFile.toPath(), new File(destinationPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /** <h2>determineLatestAddedFile</h2>
     * <li>Determines the most recently added file in the PDF folder of the active library.</li>
     *
     * @return the name of the latest added file, or an empty string if the folder is empty
     */
    public String determineLatestAddedFile(){
        String activeLibraryFilePath = CitationService.getActiveLibraryFilePath();
        String pdfFolderPath = activeLibraryFilePath.replace(AppTexts.LIBRARY_FILE_FORMAT,AppTexts.PDF_FOLDER_EXTENSION);

        File pdfFolder = new File(pdfFolderPath);
        File[] filesInPdfFolder = pdfFolder.listFiles();

        if (filesInPdfFolder != null && filesInPdfFolder.length > 0) {
            File latestFile = filesInPdfFolder[0];
            for (File file : filesInPdfFolder) {
                if (file.lastModified() > latestFile.lastModified()) {
                    latestFile = file;
                }
            }
            return latestFile.getName();
        } else {
            return "";
        }
    }
}
