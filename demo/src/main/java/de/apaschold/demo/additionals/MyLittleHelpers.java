package de.apaschold.demo.additionals;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public class MyLittleHelpers {

    private MyLittleHelpers() {
    }

    // convert Functions
    public static int convertStringInputToInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int convertStringInputToInteger(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    //add FX Elements
    public static TextField createNewTextField(String promptText){
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setStyle("-fx-font-size: 12;");

        return textField;
    }

    public static TextArea createNewTextArea(String promptText){
        TextArea textArea = new TextArea();
        textArea.setPromptText(promptText);
        textArea.setStyle("-fx-font-size: 12;");
        textArea.setMaxHeight(100.0);

        return textArea;
    }
}
