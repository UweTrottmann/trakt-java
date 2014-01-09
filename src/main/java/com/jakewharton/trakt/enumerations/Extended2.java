package com.jakewharton.trakt.enumerations;

import com.jakewharton.trakt.TraktEnumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * A newer version of the extended path parameter.
 */
public enum Extended2 implements TraktEnumeration {
    NORMAL("normal"),
    FULL("full"),
    DEFAULT("");

    private static final Map<String, Extended2> STRING_MAPPING
            = new HashMap<String, Extended2>();

    static {
        for (Extended2 via : Extended2.values()) {
            STRING_MAPPING.put(via.toString().toUpperCase(), via);
        }
    }

    private final String value;

    private Extended2(String value) {
        this.value = value;
    }

    public static Extended2 fromValue(String value) {
        return STRING_MAPPING.get(value.toUpperCase());
    }

    @Override
    public String toString() {
        return this.value;
    }
}
