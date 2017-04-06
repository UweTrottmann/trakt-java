package com.uwetrottmann.trakt5.entities;

import com.uwetrottmann.trakt5.enums.Rating;
import org.threeten.bp.OffsetDateTime;

public class SyncEpisode {

    public Integer season;
    public Integer number;
    public EpisodeIds ids;

    public OffsetDateTime collected_at;
    public OffsetDateTime watched_at;
    public OffsetDateTime rated_at;
    public Rating rating;

    public SyncEpisode number(int number) {
        this.number = number;
        return this;
    }

    public SyncEpisode season(int season) {
        this.season = season;
        return this;
    }

    public SyncEpisode id(EpisodeIds id) {
        this.ids = id;
        return this;
    }

    public SyncEpisode collectedAt(OffsetDateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    public SyncEpisode watchedAt(OffsetDateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

    public SyncEpisode ratedAt(OffsetDateTime ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    public SyncEpisode rating(Rating rating) {
        this.rating = rating;
        return this;
    }

}
