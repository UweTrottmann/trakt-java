package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

public class SyncMovie {

    public MovieIds ids;

    public DateTime collected_at;
    public DateTime watched_at;

    public SyncMovie id(MovieIds id) {
        this.ids = id;
        return this;
    }

    public SyncMovie collectedAt(DateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    public SyncMovie watchedAt(DateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

}
