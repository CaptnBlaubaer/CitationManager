package de.apaschold.demo.gui;

import de.apaschold.demo.logic.ArticleFactory;
import de.apaschold.demo.model.Article;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ImportFromBibTexViewController {
    //0. constants

    //1. attributes

    //2. FXML elements
    @FXML
    private TextArea bibTexInput;

    //3. contructor/initializemethod

    //4.FXML methods
    @FXML
    protected void importButtonClick() {
        String bibTexText = bibTexInput.getText();

        if (!bibTexText.isEmpty()) {
            importBibTex(bibTexText);
        }

        Stage stage = (Stage) bibTexInput.getScene().getWindow();
        stage.close();
    }

    //5. other methods
    private void importBibTex(String bibTexText) {
        String[] separatedImports = bibTexText.replace("@", "!!!!!@").split("!!!!!");

        for (String singleImport : separatedImports) {
            if (!singleImport.isEmpty()) {
                Article importedArticle = ArticleFactory.createArticleFromBibTex(singleImport);
                GuiController.getInstance().getArticleContainer().addArticle(importedArticle);
            }
        }
    }
}
