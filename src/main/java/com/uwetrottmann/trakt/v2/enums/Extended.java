package com.uwetrottmann.trakt.v2.enums;

public enum Extended implements TraktEnum {

    DEFAULT_MIN("min"),
    IMAGES("images"),
    FULL("full"),
    FULLIMAGES("full,images");

    private final String value;

    private Extended(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
