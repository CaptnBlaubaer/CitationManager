package de.apaschold.demo.additionals;

import de.apaschold.demo.logic.filehandling.CsvHandler;

public class TestStuff {
    public static void main(String[] args) {
        String bibTexText = "title={Modulating the fibrillization of parathyroid-hormone (PTH) peptides: azo-switches as reversible and catalytic entities},";

        System.out.println(bibTexText.replace("title={","").replace("},",""));
    }
}
