
package com.jakewharton.trakt.enumerations;

import com.jakewharton.trakt.TraktEnumeration;

import java.util.HashMap;
import java.util.Map;

public enum Extended implements TraktEnumeration {
    Extended("extended"),
    Min("min"),
    Default("");

    private static final Map<String, com.jakewharton.trakt.enumerations.Extended> STRING_MAPPING
            = new HashMap<String, com.jakewharton.trakt.enumerations.Extended>();

    static {
        for (com.jakewharton.trakt.enumerations.Extended via : com.jakewharton.trakt.enumerations
                .Extended.values()) {
            STRING_MAPPING.put(via.toString().toUpperCase(), via);
        }
    }

    private final String value;

    private Extended(String value) {
        this.value = value;
    }

    public static com.jakewharton.trakt.enumerations.Extended fromValue(String value) {
        return STRING_MAPPING.get(value.toUpperCase());
    }

    @Override
    public String toString() {
        return this.value;
    }
}
