package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

import java.util.List;

public class SyncShow {

    public ShowIds ids;
    public List<SyncSeason> seasons;

    public DateTime collected_at;
    public DateTime watched_at;

}
