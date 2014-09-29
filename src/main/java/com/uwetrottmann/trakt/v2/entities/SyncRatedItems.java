package com.uwetrottmann.trakt.v2.entities;

import java.util.LinkedList;
import java.util.List;

public class SyncRatedItems {

    public List<SyncRatedMovie> movies;
    public List<SyncRatedShow> shows;

    public SyncRatedItems movies(SyncRatedMovie movie) {
        LinkedList<SyncRatedMovie> list = new LinkedList<>();
        list.add(movie);
        return movies(list);
    }

    public SyncRatedItems movies(List<SyncRatedMovie> movies) {
        this.movies = movies;
        return this;
    }

    public SyncRatedItems shows(SyncRatedShow show) {
        LinkedList<SyncRatedShow> list = new LinkedList<>();
        list.add(show);
        return shows(list);
    }

    public SyncRatedItems shows(List<SyncRatedShow> shows) {
        this.shows = shows;
        return this;
    }

}
