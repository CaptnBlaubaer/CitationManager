package de.apaschold.demo.additionals;

import de.apaschold.demo.logic.CitationService;
import de.apaschold.demo.logic.MainViewModel;
import javafx.scene.control.Alert;

public class TestStuff {



    public static void main(String[] args) throws Exception {
        System.out.println(new MainViewModel().getSelectedCitation());
    }
}
