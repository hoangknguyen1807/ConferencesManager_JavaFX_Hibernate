package com.confmanage.converters;

import javafx.util.StringConverter;

public class BooleanConverter extends StringConverter<Boolean> {

    @Override
    public String toString(Boolean object) {
        return object.toString();
    }

    @Override
    public Boolean fromString(String string) {
        return Boolean.valueOf(string);
    }
}
