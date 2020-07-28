package com.confmanage.converters;

import javafx.util.StringConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter extends StringConverter<Date> {

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");

    @Override
    public String toString(Date object) {
        return sdf.format(object);
    }

    @Override
    public Date fromString(String string) {
        Date ret = null;
        try {
            ret = sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
