package com.confmanage.converters;

import com.confmanage.entities.Venue;
import javafx.util.StringConverter;

public class VenueConverter extends StringConverter<Venue> {
    @Override
    public String toString(Venue object) {
        return object.getName();
    }

    @Override
    public Venue fromString(String string) {
        return null;
    }
}
