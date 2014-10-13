package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

import java.util.List;

public class SyncShow {

    public ShowIds ids;
    public List<SyncSeason> seasons;

    public DateTime collected_at;
    public DateTime watched_at;

    public SyncShow id(ShowIds id) {
        this.ids = id;
        return this;
    }

    public SyncShow collectedAt(DateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    public SyncShow watchedAt(DateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

}
