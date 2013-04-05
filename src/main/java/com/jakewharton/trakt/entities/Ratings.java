package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

public class Ratings implements TraktEntity {
    private static final long serialVersionUID = -7517132370821535250L;

    public Integer percentage;
    public Integer votes;
    public Integer loved;
    public Integer hated;

}