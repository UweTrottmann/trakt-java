package com.uwetrottmann.trakt.v2.enums;

public enum HistoryType implements TraktEnum {

    MOVIES("movies"),
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
