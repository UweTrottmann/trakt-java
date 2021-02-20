package com.uwetrottmann.trakt5.entities;

import javax.annotation.Nonnull;

public class MovieIds extends BaseIds {

    public String slug;

    @Nonnull
    public static MovieIds trakt(int trakt) {
        MovieIds ids = new MovieIds();
        ids.trakt = trakt;
        return ids;
    }

    @Nonnull
    public static MovieIds imdb(String imdb) {
        MovieIds ids = new MovieIds();
        ids.imdb = imdb;
        return ids;
    }

    @Nonnull
    public static MovieIds tmdb(int tmdb) {
        MovieIds ids = new MovieIds();
        ids.tmdb = tmdb;
        return ids;
    }

    @Nonnull
    public static MovieIds slug(String slug) {
        MovieIds ids = new MovieIds();
        ids.slug = slug;
        return ids;
    }

}
