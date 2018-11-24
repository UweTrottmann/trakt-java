package com.uwetrottmann.trakt5.enums;

import java.util.HashMap;
import java.util.Map;

public enum ListPrivacy implements TraktEnum {

    PRIVATE("private"),
    FRIENDS("friends"),
    PUBLIC("public");

    public final String value;

    ListPrivacy(String value) {
        this.value = value;
    }

    private static final Map<String, ListPrivacy> STRING_MAPPING = new HashMap<>();

    static {
        for (ListPrivacy via : ListPrivacy.values()) {
            STRING_MAPPING.put(via.toString(), via);
        }
    }

    public static ListPrivacy fromValue(String value) {
        return STRING_MAPPING.get(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
