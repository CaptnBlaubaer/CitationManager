package de.apaschold.demo.gui;

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
        //populate the labels in the article overview
        this.title.setText(journalArticle.getTitle());
        this.authors.setText(journalArticle.getAuthor().replace(", ", "\n"));
        this.journal.setText(journalArticle.getJournal());
        this.journalShortForm.setText(journalArticle.getJournalShortForm());
        this.year.setText(String.valueOf(journalArticle.getYear()));
        this.volume.setText(String.valueOf(journalArticle.getVolume()));
        this.issue.setText(String.valueOf(journalArticle.getIssue()));
        this.pages.setText(journalArticle.getPages());
        this.doi.setText(journalArticle.getDoi());

        //populate the textfields in the article edit view
        this.titleChange.setText(journalArticle.getTitle());
        this.authorsChange.setText(journalArticle.getAuthor().replace(", ", "\n"));
        this.journalChange.setText(journalArticle.getJournal());
        this.journalShortFormChange.setText(journalArticle.getJournalShortForm());
        this.yearChange.setText(String.valueOf(journalArticle.getYear()));
        this.volumeChange.setText(String.valueOf(journalArticle.getVolume()));
        this.issueChange.setText(String.valueOf(journalArticle.getIssue()));
        this.pagesChange.setText(journalArticle.getPages());
        this.doiChange.setText(journalArticle.getDoi());
    }
}
