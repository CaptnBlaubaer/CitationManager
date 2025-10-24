package de.apaschold.demo.additionals;

import de.apaschold.demo.logic.filehandling.FileHandler;
import de.apaschold.demo.logic.filehandling.SeleniumWebHandlerHeadless;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

public class TestStuff {



    public static void main(String[] args) throws IOException {
        /*File file = new File("C:\\Users\\Hein\\Downloads\\s41586-025-09495-w.pdf");

        System.out.println(file.exists());
        System.out.println(file.toPath());

        Path path = file.toPath();

        System.out.println(file.lastModified());

        BasicFileAttributeView view
                = Files.getFileAttributeView(
                path, BasicFileAttributeView.class);

        BasicFileAttributes attribute
                = view.readAttributes();

        System.out.print("Creation Time of the file: ");
        System.out.println(attribute.creationTime());*/

        System.out.println(FileHandler.getInstance().determineLatestAddedFile());
    }
}
