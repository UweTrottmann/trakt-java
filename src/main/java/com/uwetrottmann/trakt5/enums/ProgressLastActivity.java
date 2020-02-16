package com.uwetrottmann.trakt5.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum ProgressLastActivity implements TraktEnum {

    COLLECTED("collected"),
    WATCHED("watched");

    private final String value;

    ProgressLastActivity(String value) {
        this.value = value;
    }

    private static final Map<String, ProgressLastActivity> STRING_MAPPING = new HashMap<>();

    static {
        for (ProgressLastActivity via : ProgressLastActivity.values()) {
            STRING_MAPPING.put(via.toString().toUpperCase(Locale.ROOT), via);
        }
    }

    public static ProgressLastActivity fromValue(String value) {
        return STRING_MAPPING.get(value.toUpperCase(Locale.ROOT));
    }

    @Override
    public String toString() {
        return value;
    }

}
