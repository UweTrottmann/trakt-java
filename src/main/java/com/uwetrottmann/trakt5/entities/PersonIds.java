package com.uwetrottmann.trakt5.entities;

import javax.annotation.Nonnull;

public class PersonIds extends BaseIds {

    public String slug;
    public String tvrage;

    @Nonnull
    public static PersonIds trakt(int trakt) {
        PersonIds ids = new PersonIds();
        ids.trakt = trakt;
        return ids;
    }

    @Nonnull
    public static PersonIds imdb(String imdb) {
        PersonIds ids = new PersonIds();
        ids.imdb = imdb;
        return ids;
    }

    @Nonnull
    public static PersonIds tmdb(int tmdb) {
        PersonIds ids = new PersonIds();
        ids.tmdb = tmdb;
        return ids;
    }

    @Nonnull
    public static PersonIds slug(String slug) {
        PersonIds ids = new PersonIds();
        ids.slug = slug;
        return ids;
    }
}
