package com.uwetrottmann.trakt.v2.entities;

import com.uwetrottmann.trakt.v2.enums.Rating;

import java.util.List;

public class SyncRatedShow {

    public Rating rating;
    public ShowIds ids;
    public List<SyncRatedSeason> seasons;

}
