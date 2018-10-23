package com.uwetrottmann.trakt5.entities;

import java.util.ArrayList;
import java.util.List;

public class SyncItems {

    public List<SyncMovie> movies;
    public List<SyncShow> shows;
    public List<SyncEpisode> episodes;
    public List<SyncPerson> people;

    /**
     * Only supported for removing specific history items.
     */
    public List<Long> ids;

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

    public SyncItems people(SyncPerson person) {
        ArrayList<SyncPerson> list = new ArrayList<>(1);
        list.add(person);
        return people(list);
    }

    public SyncItems people(List<SyncPerson> people) {
        this.people = people;
        return this;
    }

    /**
     * @deprecated use {@link #ids(long)} instead
     */
    @Deprecated
    public SyncItems ids(int id) {
        return ids((long) id);
    }

    /**
     * History id to be removed.
     */
    public SyncItems ids(long id) {
        ArrayList<Long> list = new ArrayList<>(1);
        list.add(id);
        return ids(list);
    }

    /**
     * History ids to be removed.
     */
    public SyncItems ids(List<Long> ids) {
        this.ids = ids;
        return this;
    }
}
