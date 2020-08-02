package com.confmanage.converters;

import com.confmanage.entities.Permission;
import javafx.util.StringConverter;

import java.util.Map;

public class PermissionConverter extends StringConverter<Permission> {
    @Override
    public String toString(Permission object) {
        return object.getName();
    }

    @Override
    public Permission fromString(String string) {
        for (Map.Entry<Integer, String> entry : Permission.getMap().entrySet()) {
            if (string.equals(entry.getValue())) {
                return new Permission(entry.getKey());
            }
        }
        return new Permission(Permission.USER);
    }
}
