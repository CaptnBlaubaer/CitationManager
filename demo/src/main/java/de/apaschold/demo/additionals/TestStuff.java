package de.apaschold.demo.additionals;

import de.apaschold.demo.logic.filehandling.TextFileHandler;

import java.util.Arrays;

public class TestStuff {
    public static void main(String[] args) {
        String path = "s,d";

        String[] parts = path.split("a");
        System.out.println(parts.length);
        System.out.println(Arrays.toString(parts));
    }
}
