package com.uwetrottmann.trakt5.entities;

import com.uwetrottmann.trakt5.enums.Rating;

import java.util.Date;

public class SyncMovie {

    public MovieIds ids;

    public Date collected_at;
    public Date watched_at;
    public Date rated_at;
    public Rating rating;

    public SyncMovie id(MovieIds id) {
        this.ids = id;
        return this;
    }

    public SyncMovie collectedAt(Date collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    public SyncMovie watchedAt(Date watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

    public SyncMovie ratedAt(Date ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    public SyncMovie rating(Rating rating) {
        this.rating = rating;
        return this;
    }

}
