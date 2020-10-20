package com.uwetrottmann.trakt5.enums;

import java.util.HashMap;
import java.util.Map;

public enum Audio implements TraktEnum {

    LPCM("lpcm"),
    MP3("mp3"),
    MP2("mp2"),
    AAC("aac"),
    OGG("ogg"),
    OGG_OPUS("ogg_opus"),
    WMA("wma"),
    FLAC("flac"),
    DTS("dts"),
    DTS_MA("dts_ma"),
    DTS_HR("dts_hr"),
    DTS_X("dts_x"),
    AURO_3D("auro_3d"),
    DOLBY_DIGITAL("dolby_digital"),
    DOLBY_DIGITAL_PLUS("dolby_digital_plus"),
    DOLBY_DIGITAL_PLUS_ATMOS("dolby_digital_plus_atmos"),
    DOLBY_ATMOS("dolby_atmos"),
    DOLBY_TRUEHD("dolby_truehd"),
    DOLBY_PROLOGIC("dolby_prologic");

    private final String value;

    Audio(String value) {
        this.value = value;
    }

    private static final Map<String, Audio> STRING_MAPPING = new HashMap<>();

    static {
        for (Audio via : Audio.values()) {
            STRING_MAPPING.put(via.toString(), via);
        }
    }

    public static Audio fromValue(String value) {
        return STRING_MAPPING.get(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
