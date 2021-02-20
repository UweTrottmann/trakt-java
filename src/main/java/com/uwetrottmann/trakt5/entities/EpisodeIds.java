package com.uwetrottmann.trakt5.entities;

import javax.annotation.Nonnull;

public class EpisodeIds extends BaseIds {

    public Integer tvdb;
    public Integer tvrage;

    @Nonnull
    public static EpisodeIds trakt(int trakt) {
        EpisodeIds ids = new EpisodeIds();
        ids.trakt = trakt;
        return ids;
    }

    @Nonnull
    public static EpisodeIds imdb(String imdb) {
        EpisodeIds ids = new EpisodeIds();
        ids.imdb = imdb;
        return ids;
    }

    @Nonnull
    public static EpisodeIds tmdb(int tmdb) {
        EpisodeIds ids = new EpisodeIds();
        ids.tmdb = tmdb;
        return ids;
    }

    @Nonnull
    public static EpisodeIds tvdb(int tvdb) {
        EpisodeIds ids = new EpisodeIds();
        ids.tvdb = tvdb;
        return ids;
    }

    @Nonnull
    public static EpisodeIds tvrage(int tvrage) {
        EpisodeIds ids = new EpisodeIds();
        ids.tvrage = tvrage;
        return ids;
    }

}
