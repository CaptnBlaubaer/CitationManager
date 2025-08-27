package de.apaschold.demo.gui.mainview;

import de.apaschold.demo.model.JournalArticle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class JournalArticleViewController {
    //0. constants

    //1. attributes
    private JournalArticle article;

    //2. FXML elements
    @FXML
    private Label title;
    @FXML
    private Label authors;
    @FXML
    private Label journal;
    @FXML
    private Label journalShortForm;
    @FXML
    private Label year;
    @FXML
    private Label volume;
    @FXML
    private Label issue;
    @FXML
    private Label  pages;
    @FXML
    private Label doi;

    @FXML
    private TextField titleChange;
    @FXML
    private TextArea authorsChange;
    @FXML
    private TextField journalChange;
    @FXML
    private TextField journalShortFormChange;
    @FXML
    private TextField yearChange;
    @FXML
    private TextField volumeChange;
    @FXML
    private TextField issueChange;
    @FXML
    private TextField pagesChange;
    @FXML
    private TextField doiChange;

    //3. constructors/initialize method

    //4. FXML methods
    @FXML
    private void saveChanges(){
        if (this.article != null){
            this.article.setTitle(this.titleChange.getText());
            this.article.setAuthor(this.authorsChange.getText().replace("\n", ", "));
            this.article.setJournal(this.journalChange.getText());
            this.article.setJournalShortForm(this.journalShortFormChange.getText());
            try {
                this.article.setYear(Integer.parseInt(this.yearChange.getText()));
            } catch (NumberFormatException e){
                this.article.setYear(0);
            }
            try {
                this.article.setVolume(Integer.parseInt(this.volumeChange.getText()));
            } catch (NumberFormatException e){
                this.article.setVolume(0);
            }
            try {
                this.article.setIssue(Integer.parseInt(this.issueChange.getText()));
            } catch (NumberFormatException e){
                this.article.setIssue(0);
            }
            this.article.setPages(this.pagesChange.getText());
            this.article.setDoi(this.doiChange.getText());

            //update the labels in the article overview
            selectJournalArticle(this.article);
        }
    }

    //5. other methods
    public void selectJournalArticle(JournalArticle journalArticle){
        this.article = journalArticle;

        String yearAsString = "-";
        if (this.article.getYear() != 0){
            yearAsString = String.valueOf(this.article.getYear());
        }

        String volumeAsString = "-";
        if (this.article.getVolume() != 0){
            volumeAsString = String.valueOf(this.article.getVolume());
        }

        String issueAsString = "-";
        if (this.article.getIssue() != 0){
            issueAsString = String.valueOf(this.article.getIssue());
        }

        //populate the labels in the article overview
        populateArticleOverviewTab(yearAsString, volumeAsString, issueAsString);

        //populate the textfields in the article edit view
        populateArticleEditTab(yearAsString, volumeAsString, issueAsString);

    }

    private void populateArticleOverviewTab(String yearAsString, String volumeAsString, String issueAsString) {
        this.title.setText(this.article.getTitle());
        this.authors.setText(this.article.getAuthor().replace(", ", "\n"));
        this.journal.setText(this.article.getJournal());
        this.journalShortForm.setText(this.article.getJournalShortForm());
        this.year.setText(yearAsString);
        this.volume.setText(volumeAsString);
        this.issue.setText(issueAsString);
        this.pages.setText(this.article.getPages());
        this.doi.setText(this.article.getDoi());
    }

    private void populateArticleEditTab(String yearAsString,String volumeAsString,String issueAsString) {
        this.titleChange.setText(this.article.getTitle());
        this.authorsChange.setText(this.article.getAuthor().replace(", ", "\n"));
        this.journalChange.setText(this.article.getJournal());
        this.journalShortFormChange.setText(this.article.getJournalShortForm());
        this.yearChange.setText(yearAsString);
        this.volumeChange.setText(volumeAsString);
        this.issueChange.setText(issueAsString);
        this.pagesChange.setText(this.article.getPages());
        this.doiChange.setText(this.article.getDoi());
    }
}
