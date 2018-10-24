package com.uwetrottmann.trakt5.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum SortBy implements TraktEnum {
    RANK("rank"),
    ADDED("added"),
    TITLE("title"),
    RELEASED("released"),
    RUNTIME("runtime"),
    POPULARITY("popularity"),
    PERCENTAGE("percentage"),
    VOTES("votes"),
    MY_RATING("my_rating"),
    RANDOM("random");

    private final String value;

    SortBy(String value) {
        this.value = value;
    }

    private static final Map<String, SortBy> STRING_MAPPING = new HashMap<>();

    static {
        for (SortBy via : SortBy.values()) {
            STRING_MAPPING.put(via.toString().toUpperCase(Locale.ROOT), via);
        }
    }

    public static SortBy fromValue(String value) {
        return STRING_MAPPING.get(value.toUpperCase(Locale.ROOT));
    }

    @Override
    public String toString() {
        return value;
    }

}
