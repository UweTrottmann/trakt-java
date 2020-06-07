package com.uwetrottmann.trakt5.enums;

import java.util.HashMap;
import java.util.Map;

public enum MediaType implements TraktEnum {

    DIGITAL("digital"),
    BLURAY("bluray"),
    HDDVD("hddvd"),
    DVD("dvd"),
    VCD("vcd"),
    VHS("vhs"),
    BETAMAX("betamax"),
    LASERDISC("laserdisc");

    private final String value;

    MediaType(String value) {
        this.value = value;
    }

    private static final Map<String, MediaType> STRING_MAPPING = new HashMap<>();

    static {
        for (MediaType via : MediaType.values()) {
            STRING_MAPPING.put(via.toString(), via);
        }
    }

    public static MediaType fromValue(String value) {
        return STRING_MAPPING.get(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
