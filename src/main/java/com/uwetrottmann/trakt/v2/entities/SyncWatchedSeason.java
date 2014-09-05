package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

import java.util.List;

public class SyncWatchedSeason {

    public DateTime watched_at;
    public int number;
    public List<SyncWatchedEpisode> episodes;

}
