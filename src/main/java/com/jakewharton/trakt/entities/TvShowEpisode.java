package com.jakewharton.trakt.entities;

import com.google.gson.annotations.SerializedName;
import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.Rating;

import java.util.Calendar;
import java.util.Date;

public class TvShowEpisode implements TraktEntity {
    private static final long serialVersionUID = -1550739539663499211L;

    public Integer season;
    public Integer number;
    public String title;
    public String overview;
    public String url;
    @SerializedName("first_aired") public Date firstAired;
    public Calendar inserted;
    public Integer plays;
    public Images images;
    public Ratings ratings;
    public Boolean watched;
    public Rating rating;
    public Rating rating_advanced;
    @SerializedName("in_watchlist") public Boolean inWatchlist;

}
