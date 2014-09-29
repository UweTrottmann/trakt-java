package com.uwetrottmann.trakt.v2.entities;

import java.util.LinkedList;
import java.util.List;

public class SyncWatchedItems {

    public List<SyncWatchedMovie> movies;
    public List<SyncWatchedShow> shows;

    public SyncWatchedItems movies(SyncWatchedMovie movie) {
        LinkedList<SyncWatchedMovie> list = new LinkedList<>();
        list.add(movie);
        return movies(list);
    }

    public SyncWatchedItems movies(List<SyncWatchedMovie> movies) {
        this.movies = movies;
        return this;
    }

    public SyncWatchedItems shows(SyncWatchedShow show) {
        LinkedList<SyncWatchedShow> list = new LinkedList<>();
        list.add(show);
        return shows(list);
    }

    public SyncWatchedItems shows(List<SyncWatchedShow> shows) {
        this.shows = shows;
        return this;
    }

}
