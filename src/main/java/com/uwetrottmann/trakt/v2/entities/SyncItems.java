package com.uwetrottmann.trakt.v2.entities;

import java.util.Arrays;
import java.util.List;

public class SyncItems {

    public List<SyncMovie> movies;
    public List<SyncShow> shows;

    public SyncItems movies(SyncMovie... movies) {
        this.movies = Arrays.asList(movies);
        return this;
    }

    public SyncItems shows(SyncShow... shows) {
        this.shows = Arrays.asList(shows);
        return this;
    }

}
