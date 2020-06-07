package com.uwetrottmann.trakt5.enums;

import java.util.HashMap;
import java.util.Map;

public enum Hdr implements TraktEnum {

    DOLBY_VISION("dolby_vision"),
    HDR10("hdr10"),
    HDR10_PLUS("hdr10_plus"),
    HLG("hlg");

    private final String value;

    Hdr(String value) {
        this.value = value;
    }

    private static final Map<String, Hdr> STRING_MAPPING = new HashMap<>();

    static {
        for (Hdr via : Hdr.values()) {
            STRING_MAPPING.put(via.toString(), via);
        }
    }

    public static Hdr fromValue(String value) {
        return STRING_MAPPING.get(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
