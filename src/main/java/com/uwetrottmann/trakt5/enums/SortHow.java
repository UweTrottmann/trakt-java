package com.uwetrottmann.trakt5.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum SortHow implements TraktEnum {
    ASC("asc"),
    DESC("desc");

    private final String value;

    SortHow(String value) {
        this.value = value;
    }

    private static final Map<String, SortHow> STRING_MAPPING = new HashMap<>();

    static {
        for (SortHow via : SortHow.values()) {
            STRING_MAPPING.put(via.toString().toUpperCase(Locale.ROOT), via);
        }
    }

    public static SortHow fromValue(String value) {
        return STRING_MAPPING.get(value.toUpperCase(Locale.ROOT));
    }

    @Override
    public String toString() {
        return value;
    }

}
