package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

public class SyncEpisode {

    public int number;
    public EpisodeIds ids;

    public DateTime collected_at;
    public DateTime watched_at;

    public SyncEpisode number(int number) {
        this.number = number;
        return this;
    }

    public SyncEpisode id(EpisodeIds id) {
        this.ids = id;
        return this;
    }

    public SyncEpisode collectedAt(DateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    public SyncEpisode watchedAt(DateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

}
