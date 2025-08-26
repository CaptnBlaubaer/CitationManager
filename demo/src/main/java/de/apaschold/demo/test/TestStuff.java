package de.apaschold.demo.test;

import de.apaschold.demo.logic.filehandling.CsvHandler;

public class TestStuff {
    public static void main(String[] args) {
        System.out.println(CsvHandler.getInstance().readArticleInfosCsvFile());
    }
}
