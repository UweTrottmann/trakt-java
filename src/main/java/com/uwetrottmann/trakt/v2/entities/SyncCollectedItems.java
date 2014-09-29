package com.uwetrottmann.trakt.v2.entities;

import java.util.LinkedList;
import java.util.List;

public class SyncCollectedItems {

    public List<SyncCollectedMovie> movies;
    public List<SyncCollectedShow> shows;

    public SyncCollectedItems movies(SyncCollectedMovie movie) {
        LinkedList<SyncCollectedMovie> list = new LinkedList<>();
        list.add(movie);
        return movies(list);
    }

    public SyncCollectedItems movies(List<SyncCollectedMovie> movies) {
        this.movies = movies;
        return this;
    }

    public SyncCollectedItems shows(SyncCollectedShow show) {
        LinkedList<SyncCollectedShow> list = new LinkedList<>();
        list.add(show);
        return shows(list);
    }

    public SyncCollectedItems shows(List<SyncCollectedShow> shows) {
        this.shows = shows;
        return this;
    }

}
