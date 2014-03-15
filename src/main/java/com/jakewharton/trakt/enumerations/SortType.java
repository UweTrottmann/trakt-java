package com.jakewharton.trakt.enumerations;

import com.jakewharton.trakt.TraktEnumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Sort type path parameter
 */
public enum SortType implements TraktEnumeration {
    TITLE("title"),
    ACTIVITY("activity"),
    MOST_COMPLETED("most-completed"),
    LEAST_COMPLETED("least-completed"),
    RECENTLY_AIRED("recently-aired"),
    PREVIOUSLY_AIRED("previously-aired"),
    DEFAULT("title");

    private final String value;

    private SortType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    private static final Map<String, SortType> STRING_MAPPING = new HashMap<String, SortType>();

    static {
        for (SortType via : SortType.values()) {
            STRING_MAPPING.put(via.toString(), via);
        }
    }

    public static SortType fromValue(String value) {
        return STRING_MAPPING.get(value);
    }
}
