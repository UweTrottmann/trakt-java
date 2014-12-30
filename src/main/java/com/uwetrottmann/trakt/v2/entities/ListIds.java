package com.uwetrottmann.trakt.v2.entities;

public class ListIds {

    public Integer trakt;
    public String slug;

    public static ListIds trakt(int trakt) {
        ListIds ids = new ListIds();
        ids.trakt = trakt;
        return ids;
    }

    public static ListIds slug(String slug) {
        ListIds ids = new ListIds();
        ids.slug = slug;
        return ids;
    }

}
