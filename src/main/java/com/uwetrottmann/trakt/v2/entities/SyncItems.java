package com.uwetrottmann.trakt.v2.entities;

import java.util.LinkedList;
import java.util.List;

public class SyncItems {

    public List<SyncMovie> movies;
    public List<SyncShow> shows;

    public SyncItems movies(SyncMovie movie) {
        LinkedList<SyncMovie> list = new LinkedList<>();
        list.add(movie);
        return movies(list);
    }

    public SyncItems movies(List<SyncMovie> movies) {
        this.movies = movies;
        return this;
    }

    public SyncItems shows(SyncShow show) {
        LinkedList<SyncShow> list = new LinkedList<>();
        list.add(show);
        return shows(list);
    }

    public SyncItems shows(List<SyncShow> shows) {
        this.shows = shows;
        return this;
    }

}
