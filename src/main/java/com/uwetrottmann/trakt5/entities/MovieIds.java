package com.uwetrottmann.trakt5.entities;

public class MovieIds extends BaseIds {

    public String slug;

    public static MovieIds trakt(int trakt) {
        MovieIds ids = new MovieIds();
        ids.trakt = trakt;
        return ids;
    }

    public static MovieIds imdb(String imdb) {
        MovieIds ids = new MovieIds();
        ids.imdb = imdb;
        return ids;
    }

    public static MovieIds tmdb(int tmdb) {
        MovieIds ids = new MovieIds();
        ids.tmdb = tmdb;
        return ids;
    }

    public static MovieIds slug(String slug) {
        MovieIds ids = new MovieIds();
        ids.slug = slug;
        return ids;
    }

}
