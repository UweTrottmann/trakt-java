package com.uwetrottmann.trakt.v2.entities;

public class ShowIds extends BaseIds {

    public String slug;
    public Integer tvdb;
    public Integer tvrage;

    public static ShowIds trakt(int trakt) {
        ShowIds ids = new ShowIds();
        ids.trakt = trakt;
        return ids;
    }

    public static ShowIds imdb(String imdb) {
        ShowIds ids = new ShowIds();
        ids.imdb = imdb;
        return ids;
    }

    public static ShowIds tmdb(int tmdb) {
        ShowIds ids = new ShowIds();
        ids.tmdb = tmdb;
        return ids;
    }

    public static ShowIds slug(String slug) {
        ShowIds ids = new ShowIds();
        ids.slug = slug;
        return ids;
    }

    public static ShowIds tvdb(int tvdb) {
        ShowIds ids = new ShowIds();
        ids.tvdb = tvdb;
        return ids;
    }

    public static ShowIds tvrage(int tvrage) {
        ShowIds ids = new ShowIds();
        ids.tvrage = tvrage;
        return ids;
    }

}
