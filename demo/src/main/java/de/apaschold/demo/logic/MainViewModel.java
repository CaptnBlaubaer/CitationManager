package de.apaschold.demo.logic;

import de.apaschold.demo.model.AbstractCitation;
import de.apaschold.demo.model.CitationViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainViewModel {
    //0. constants

    //1. attributes
    private CitationService citationService;

    private final ObservableList<CitationViewModel> citations = FXCollections.observableArrayList();
    private final ObjectProperty<CitationViewModel> selected = new SimpleObjectProperty<>();

    public MainViewModel () {
        this.citationService = new CitationService();
        loadCitations();

        this.selected.set(this.citations.getFirst());
    }

    private void loadCitations(){
        citationService.findAll().stream()
                .map(CitationViewModel::new)
                .forEach(citations::add);
    }

    public ObjectProperty<CitationViewModel> getSelectedCitation() { return selected;}
    public ObservableList<CitationViewModel> getCitations() { return citations;}
}
