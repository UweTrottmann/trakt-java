package com.uwetrottmann.trakt5.enums;

public enum HistoryType implements TraktEnum {

    MOVIES("movies"),
    SHOWS("shows"),
    SEASONS("seasons"),
    EPISODES("episodes");

    private final String value;

    HistoryType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
