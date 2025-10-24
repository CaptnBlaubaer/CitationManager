package de.apaschold.demo.logic.filehandling;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.gui.GuiController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


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
    public void createEmptyLibrary(File newLibraryFile){
        try (FileWriter writer = new FileWriter(newLibraryFile , StandardCharsets.UTF_8)) {
            writer.write("");

            String pdfDirectoryPath = newLibraryFile.getAbsolutePath().replace(AppTexts.LIBRARY_FILE_FORMAT, AppTexts.PDF_FOLDER_EXTENSION);

            File theDir = new File(pdfDirectoryPath);
            if (!theDir.exists()){
                theDir.mkdirs();
            }
        } catch (IOException e) {
            System.err.println("Error saving to File: " );
        }
    }

    public void deleteSelectedAttachmentFromFolder(String chosenAttachment) {
        String activeLibraryFilePath = GuiController.getInstance().getActiveLibraryFilePath();
        String pdfFolderPath = activeLibraryFilePath.replace(AppTexts.LIBRARY_FILE_FORMAT,AppTexts.PDF_FOLDER_EXTENSION);

        File attachmentToDelete = new File(pdfFolderPath + chosenAttachment);
        if (!attachmentToDelete.isDirectory()) {
            attachmentToDelete.delete();
        }
    }

    public void copySelectedAttachmentToPdfFolder(File chosenFile) {
        String activeLibraryFilePath = GuiController.getInstance().getActiveLibraryFilePath();
        String pdfFolderPath = activeLibraryFilePath.replace(AppTexts.LIBRARY_FILE_FORMAT,AppTexts.PDF_FOLDER_EXTENSION);

        String chosenFileName = chosenFile.getName();
        String destinationPath = pdfFolderPath + chosenFileName;

        try {
            Files.copy(chosenFile.toPath(), new File(destinationPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String determineLatestAddedFile(){
        String activeLibraryFilePath = GuiController.getInstance().getActiveLibraryFilePath();
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
