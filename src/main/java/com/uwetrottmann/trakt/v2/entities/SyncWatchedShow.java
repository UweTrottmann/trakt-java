package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

import java.util.List;

public class SyncWatchedShow {

    public DateTime watched_at;
    public ShowIds ids;
    public List<SyncWatchedSeason> seasons;

}
