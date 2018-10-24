package com.uwetrottmann.trakt5.enums;

public enum SortHow implements TraktEnum {
    ASC("asc"),
    DESC("desc");

    private final String value;

    SortHow(String value) {
        this.value = value;
    }

    public static SortHow fromValue(String value) {
        for (SortHow sortHow : values()) {
            if(sortHow.value.equals(value)) {
                return sortHow;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return value;
    }

}
