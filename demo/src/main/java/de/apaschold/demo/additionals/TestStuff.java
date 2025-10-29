package de.apaschold.demo.additionals;

import de.apaschold.demo.logic.CitationFactory;
import de.apaschold.demo.logic.CitationLibrary;
import de.apaschold.demo.logic.filehandling.FileHandler;
import de.apaschold.demo.logic.filehandling.SeleniumWebHandlerHeadless;
import de.apaschold.demo.model.Citation;
import de.apaschold.demo.model.CitationType;
import de.apaschold.demo.model.Unpublished;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

public class TestStuff {



    public static void main(String[] args) throws IOException {
        CitationLibrary library = new CitationLibrary("C:\\Users\\apasc\\OneDrive\\Desktop\\ProgrammierStuff\\CitationManagerJava\\Libraries\\MyFirstLibrary.cml");
        Citation citation = library.getFirstCitation();

        System.out.println(citation.toCsvStringTest());
    }
}
