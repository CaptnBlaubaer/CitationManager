package de.apaschold.demo.logic.filehandling;

import de.apaschold.demo.additionals.AppTexts;
import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.logic.ArticleFactory;
import de.apaschold.demo.model.ArticleReference;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

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

            String pdfDirectoryPath = newLibraryFile.getAbsolutePath().replace(AppTexts.LIBRARY_FILE_FORMAT, AppTexts.FOLDER_EXTENSION);

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
        String pdfFolderPath = activeLibraryFilePath.replace(AppTexts.LIBRARY_FILE_FORMAT,AppTexts.FOLDER_EXTENSION);

        File attachmentToDelete = new File(pdfFolderPath + "\\" + chosenAttachment);
        if (!attachmentToDelete.isDirectory()) {
            attachmentToDelete.delete();
        }
    }

    public void copySelectedAttachmentToPdfFolder(File chosenFile) {
        String activeLibraryFilePath = GuiController.getInstance().getActiveLibraryFilePath();
        String pdfFolderPath = activeLibraryFilePath.replace(AppTexts.LIBRARY_FILE_FORMAT,AppTexts.FOLDER_EXTENSION);

        String chosenFileName = chosenFile.getName();
        String destinationPath = pdfFolderPath + "\\" + chosenFileName;

        try {
            Files.copy(chosenFile.toPath(), new File(destinationPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
