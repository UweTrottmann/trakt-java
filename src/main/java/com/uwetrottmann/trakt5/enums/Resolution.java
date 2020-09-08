package com.uwetrottmann.trakt5.enums;

import java.util.HashMap;
import java.util.Map;

public enum Resolution implements TraktEnum {

    UHD_4K("uhd_4k"),
    HD_1080P("hd_1080p"),
    HD_1080I("hd_1080i"),
    HD_720P("hd_720p"),
    SD_480P("sd_480p"),
    SD_480I("sd_480i"),
    SD_576P("sd_576p"),
    SD_576I("sd_576i");

    private final String value;

    Resolution(String value) {
        this.value = value;
    }

    private static final Map<String, Resolution> STRING_MAPPING = new HashMap<>();

    static {
        for (Resolution via : Resolution.values()) {
            STRING_MAPPING.put(via.toString(), via);
        }
    }

    public static Resolution fromValue(String value) {
        return STRING_MAPPING.get(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
