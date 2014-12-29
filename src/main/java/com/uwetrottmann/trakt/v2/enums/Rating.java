package com.uwetrottmann.trakt.v2.enums;

public enum Rating implements TraktEnum {

    WEAKSAUCE(1),
    TERRIBLE(2),
    BAD(3),
    POOR(4),
    MEH(5),
    FAIR(6),
    GOOD(7),
    GREAT(8),
    SUPERB(9),
    TOTALLYNINJA(10);

    public int value;

    private Rating(int value) {
        this.value = value;
    }

    public static Rating fromValue(int value) {
        return Rating.values()[value - 1];
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
