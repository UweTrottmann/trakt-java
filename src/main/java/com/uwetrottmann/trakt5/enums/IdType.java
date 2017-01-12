package com.uwetrottmann.trakt5.enums;

public enum IdType implements TraktEnum {

    TRAKT("trakt"),
    IMDB("imdb"),
    TMDB("tmdb"),
    TVDB("tvdb"),
    TVRAGE("tvrage");

    private final String value;

    IdType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
