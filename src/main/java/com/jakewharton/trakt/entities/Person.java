package com.jakewharton.trakt.entities;

import com.google.gson.annotations.SerializedName;
import com.jakewharton.trakt.TraktEntity;

import java.util.Date;

public class Person implements TraktEntity {
    private static final long serialVersionUID = -4755476212550445673L;

    public String name;
    public String url;
    public String biography;
    public Date birthday;
    public String birthplace;
    @SerializedName("tmdb_id") public Integer tmdbId;
    public Images images;

}
