package com.uwetrottmann.trakt5.entities;

import java.util.List;

public class SyncErrors {

    public List<SyncMovie> movies;
    public List<SyncShow> shows;
    public List<SyncSeason> seasons;
    public List<SyncEpisode> episodes;
    public List<SyncPerson> people;
    public List<Long> ids;

}
