package com.uwetrottmann.trakt.v2.entities;

import com.uwetrottmann.trakt.v2.enums.Rating;

import java.util.List;

public class SyncRatedSeason {

    public Rating rating;
    public int number;
    public List<SyncRatedEpisode> episodes;

}
