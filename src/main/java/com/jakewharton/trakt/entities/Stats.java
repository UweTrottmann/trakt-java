package com.jakewharton.trakt.entities;

public class Stats {

    public Ratings ratings;

    public int watchers;

    public int plays;

    public AllAndUserStats scrobbles;

    public AllAndUserStats checkins;

    public AllAndUserStats collection;

    public Lists lists;

    public Comments comments;

    public static class AllAndUserStats {

        public int all;

        public int users;
    }

    public static class Lists {

        public int all;

        public int watchlist;

        public int custom;
    }

    public static class Comments {

        public int all;

        public int reviews;

        public int shouts;
    }

}
