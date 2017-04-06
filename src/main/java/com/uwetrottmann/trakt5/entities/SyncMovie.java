package com.uwetrottmann.trakt5.entities;

import com.uwetrottmann.trakt5.enums.Rating;
import org.threeten.bp.OffsetDateTime;

public class SyncMovie {

    public MovieIds ids;

    public OffsetDateTime collected_at;
    public OffsetDateTime watched_at;
    public OffsetDateTime rated_at;
    public Rating rating;

    public SyncMovie id(MovieIds id) {
        this.ids = id;
        return this;
    }

    public SyncMovie collectedAt(OffsetDateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    public SyncMovie watchedAt(OffsetDateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

    public SyncMovie ratedAt(OffsetDateTime ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    public SyncMovie rating(Rating rating) {
        this.rating = rating;
        return this;
    }

}
