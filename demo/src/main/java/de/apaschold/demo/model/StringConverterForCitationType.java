package de.apaschold.demo.model;

import javafx.util.StringConverter;
import de.apaschold.demo.model.CitationType;

import java.util.Arrays;

public class StringConverterForCitationType extends StringConverter<CitationType> {
    @Override
    public String toString(CitationType citationType) {
        return citationType == null ? "" : citationType.getDescription();
    }

    @Override
    public CitationType fromString(String string) {
        // not used by ComboBox, but must be implemented
        return Arrays.stream(CitationType.values())
                .filter(e -> e.getDescription().equals(string))
                .findFirst().orElse(null);
    }
}
