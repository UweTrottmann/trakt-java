package com.uwetrottmann.trakt5.enums;

public enum Type implements TraktEnum {

    MOVIE("movie"),
    SHOW("show"),
    EPISODE("episode"),
    PERSON("person"),
    LIST("list");

    private final String value;

    private Type(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
