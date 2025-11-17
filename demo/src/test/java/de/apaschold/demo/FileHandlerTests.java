package de.apaschold.demo;

import de.apaschold.demo.gui.GuiController;
import de.apaschold.demo.logic.filehandling.FileHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileHandlerTests {

    private static final List<File> CREATED_TEST_FILES = new ArrayList<>();

    @Test
    void testCreateEmptyLibrary(){
        File testFile = new File("C:/Users/Hein/Desktop/Programmierstuff/JAva/CitationManagerTestLibrary/test.db");
        File testFilePdfDirectory = new File("C:/Users/Hein/Desktop/Programmierstuff/JAva/CitationManagerTestLibrary/test-pdfs");

        CREATED_TEST_FILES.add(testFile);
        CREATED_TEST_FILES.add(testFilePdfDirectory);

        FileHandler.getInstance().createEmptyLibrary(testFile);


        assert testFile.exists() : "Test file was not created.";
        assert testFilePdfDirectory.exists() : "Test PDF directory was not created.";
        assert testFilePdfDirectory.isDirectory() : "Test PDF directory is not a directory.";
    }

    @AfterAll
    static void removeTestFiles(){
        for (File testFile : CREATED_TEST_FILES){

            if(testFile.exists()){
                try{
                    Files.delete(testFile.toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}
