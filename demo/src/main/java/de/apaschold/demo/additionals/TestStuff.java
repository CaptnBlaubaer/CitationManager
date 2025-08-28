package de.apaschold.demo.additionals;

import de.apaschold.demo.logic.filehandling.TextFileHandler;

public class TestStuff {
    public static void main(String[] args) {
        System.out.println(TextFileHandler.getInstance().loadLibraryFilePath());
    }
}
