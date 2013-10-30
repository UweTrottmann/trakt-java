package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.enumerations.Rating;

import java.util.Date;

public class TvShowEpisode {

    public int season;

    public int number;

    public String imdb_id;

    public int tvdb_id;

    public String title;

    public String overview;

    public String url;

    public Date first_aired;

    public long first_aired_utc;

    public Images images;

    public Ratings ratings;

    public Boolean watched;

    public Integer plays;

    public Rating rating_advanced;

    public Boolean in_watchlist;

    public Boolean in_collection;

}
