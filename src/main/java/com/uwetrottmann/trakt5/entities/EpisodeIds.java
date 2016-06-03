package com.uwetrottmann.trakt5.entities;

public class EpisodeIds extends BaseIds {

    public Integer tvdb;
    public Integer tvrage;


    public static EpisodeIds trakt(int trakt) {
        EpisodeIds ids = new EpisodeIds();
        ids.trakt = trakt;
        return ids;
    }

    public static EpisodeIds imdb(String imdb) {
        EpisodeIds ids = new EpisodeIds();
        ids.imdb = imdb;
        return ids;
    }

    public static EpisodeIds tmdb(int tmdb) {
        EpisodeIds ids = new EpisodeIds();
        ids.tmdb = tmdb;
        return ids;
    }

    public static EpisodeIds tvdb(int tvdb) {
        EpisodeIds ids = new EpisodeIds();
        ids.tvdb = tvdb;
        return ids;
    }

    public static EpisodeIds tvrage(int tvrage) {
        EpisodeIds ids = new EpisodeIds();
        ids.tvrage = tvrage;
        return ids;
    }

}
