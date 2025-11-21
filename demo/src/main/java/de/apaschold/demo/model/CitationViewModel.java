package de.apaschold.demo.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CitationViewModel {
    //0. constants

    //1. attributes
    private final AbstractCitation citationModel;

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty author = new SimpleStringProperty();
    private final StringProperty journal = new SimpleStringProperty();
    private final IntegerProperty year = new SimpleIntegerProperty();

    //2. constructors
    public CitationViewModel(AbstractCitation citationModel) {
        this.citationModel = citationModel;
        this.id.set(citationModel.getId());
        this.title.set(citationModel.getTitle());
        this.author.set(citationModel.getAuthor());
        this.journal.set(citationModel.getJournal());
        this.year.set(citationModel.getYear());

        this.title.addListener((observable, oldValue, newValue) -> citationModel.setTitle(newValue));
        this.author.addListener((observable, oldValue, newValue) -> citationModel.setAuthors(newValue));
        this.journal.addListener((observable, oldValue, newValue) -> citationModel.setJournal(newValue));
        this.year.addListener((observable, oldValue, newValue) -> citationModel.setYear(newValue.intValue()));
    }

    //3. getter and setter methods
    public IntegerProperty idProperty() { return id;}
    public StringProperty titleProperty() { return title;}
    public StringProperty authorProperty() { return author;}
    public StringProperty journalProperty() { return journal;}
    public IntegerProperty yearProperty() { return year;}

    public AbstractCitation getCitationModel() { return citationModel;}
}
