package com.uwetrottmann.trakt5.enums;

public enum IdType implements TraktEnum {

    TRAKT_MOVIE("trakt-movie"),
    TRAKT_SHOW("trakt-show"),
    TRAKT_EPISODE("trakt-episode"),
    IMDB("imdb"),
    TMDB("tmdb"),
    TVDB("tvdb"),
    TVRAGE("tvrage");

    private final String value;

    private IdType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
