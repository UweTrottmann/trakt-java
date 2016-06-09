package com.uwetrottmann.trakt5.enums;

/**
 * By default, all methods will return minimal info for movies, shows, episodes, and users. Minimal info is typically
 * all you need to match locally cached items and includes the title, year, and ids. However, you can request different
 * extended levels of information.
 */
public enum Extended implements TraktEnum {

    /** Default. Returns enough info to match locally. */
    DEFAULT_MIN("min"),
    /** Minimal info and all images. */
    IMAGES("images"),
    /** Complete info for an item. */
    FULL("full"),
    /** Complete info and all images. */
    FULLIMAGES("full,images"),
    /** Only works with sync watchedShows. */
    NOSEASONS("noseasons"),
    /** Only works with sync watchedShows. */
    NOSEASONSIMAGES("noseasons,images");

    private final String value;

    Extended(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
