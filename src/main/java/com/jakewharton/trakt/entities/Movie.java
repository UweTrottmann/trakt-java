package com.jakewharton.trakt.entities;

import com.google.gson.annotations.SerializedName;

import com.jakewharton.trakt.TraktEntity;

import java.util.Date;

public class Movie extends MediaBase implements TraktEntity {
    private static final long serialVersionUID = -1543214252495012419L;

    @SerializedName("tmdb_id") public int tmdbId;
    public Integer plays;
    @SerializedName("in_collection") public Boolean inCollection;
    public Date released;
    public String trailer;
    public Integer runtime;
    public String tagline;
    public String overview;
    public String certification;
    public Boolean watched;

}
