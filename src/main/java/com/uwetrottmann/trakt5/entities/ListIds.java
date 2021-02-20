package com.uwetrottmann.trakt5.entities;

import javax.annotation.Nonnull;

public class ListIds {

    public Integer trakt;
    public String slug;

    @Nonnull
    public static ListIds trakt(int trakt) {
        ListIds ids = new ListIds();
        ids.trakt = trakt;
        return ids;
    }

    @Nonnull
    public static ListIds slug(String slug) {
        ListIds ids = new ListIds();
        ids.slug = slug;
        return ids;
    }

}
