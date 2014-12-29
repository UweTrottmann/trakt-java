package com.uwetrottmann.trakt.v2.entities;

import com.uwetrottmann.trakt.v2.enums.Rating;
import org.joda.time.DateTime;

public class SyncEpisode {

    public Integer number;
    public EpisodeIds ids;

    public DateTime collected_at;
    public DateTime watched_at;
    public DateTime rated_at;
    public Rating rating;

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

    public SyncEpisode ratedAt(DateTime ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    public SyncEpisode rating(Rating rating) {
        this.rating = rating;
        return this;
    }

}
