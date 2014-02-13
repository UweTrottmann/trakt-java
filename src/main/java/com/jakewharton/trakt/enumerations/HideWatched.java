package com.jakewharton.trakt.enumerations;

import java.util.HashMap;
import java.util.Map;

public enum HideWatched {
    HIDE_WATCHED("hidewatched"),
    DEFAULT("");

    private static final Map<String, HideWatched> STRING_MAPPING
            = new HashMap<String, HideWatched>();

    static {
        for (HideWatched via : HideWatched.values()) {
            STRING_MAPPING.put(via.toString().toUpperCase(), via);
        }
    }

    private final String value;

    private HideWatched(String value) {
        this.value = value;
    }

    public static HideWatched fromValue(String value) {
        return STRING_MAPPING.get(value.toUpperCase());
    }

    @Override
    public String toString() {
        return this.value;
    }
}
