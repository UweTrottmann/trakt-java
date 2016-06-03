package com.uwetrottmann.trakt5.entities;

import java.util.ArrayList;
import java.util.List;

public class SyncItems {

    public List<SyncMovie> movies;
    public List<SyncShow> shows;
    public List<SyncEpisode> episodes;
    /**
     * Only supported for removing specific history items.
     */
    public List<Integer> ids;

    public SyncItems movies(SyncMovie movie) {
        ArrayList<SyncMovie> list = new ArrayList<>(1);
        list.add(movie);
        return movies(list);
    }

    public SyncItems movies(List<SyncMovie> movies) {
        this.movies = movies;
        return this;
    }

    public SyncItems shows(SyncShow show) {
        ArrayList<SyncShow> list = new ArrayList<>(1);
        list.add(show);
        return shows(list);
    }

    public SyncItems shows(List<SyncShow> shows) {
        this.shows = shows;
        return this;
    }

    public SyncItems episodes(SyncEpisode episode) {
        ArrayList<SyncEpisode> list = new ArrayList<>(1);
        list.add(episode);
        return episodes(list);
    }

    public SyncItems episodes(List<SyncEpisode> episodes) {
        this.episodes = episodes;
        return this;
    }

    /**
     * History id to be removed.
     */
    public SyncItems ids(int id) {
        ArrayList<Integer> list = new ArrayList<>(1);
        list.add(id);
        return ids(list);
    }

    /**
     * History ids to be removed.
     */
    public SyncItems ids(List<Integer> ids) {
        this.ids = ids;
        return this;
    }

}
