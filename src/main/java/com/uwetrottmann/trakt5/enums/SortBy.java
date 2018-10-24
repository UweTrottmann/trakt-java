package com.uwetrottmann.trakt5.enums;

public enum SortBy implements TraktEnum {
    RANK("rank"),
    ADDED("added"),
    TITLE("title"),
    RELEASED("released"),
    RUNTIME("runtime"),
    POPULARITY("popularity"),
    PERCENTAGE("percentage"),
    VOTES("votes"),
    MY_RATING("my_rating"),
    RANDOM("random");

    private final String value;

    SortBy(String value) {
        this.value = value;
    }

    public static SortBy fromValue(String value) {
        for (SortBy sortBy : values()) {
            if(sortBy.value.equals(value)) {
                return sortBy;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return value;
    }

}
