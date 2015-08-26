package com.uwetrottmann.trakt.v2.entities;

import com.uwetrottmann.trakt.v2.enums.Rating;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class SyncShow {

    public ShowIds ids;
    public List<SyncSeason> seasons;

    public DateTime collected_at;
    public DateTime watched_at;
    public DateTime rated_at;
    public Rating rating;

    public SyncShow id(ShowIds id) {
        this.ids = id;
        return this;
    }

    public SyncShow seasons(List<SyncSeason> seasons) {
        this.seasons = seasons;
        return this;
    }

    public SyncShow seasons(SyncSeason season) {
        ArrayList<SyncSeason> list = new ArrayList<>(1);
        list.add(season);
        return seasons(list);
    }

    public SyncShow collectedAt(DateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    public SyncShow watchedAt(DateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

    public SyncShow ratedAt(DateTime ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    public SyncShow rating(Rating rating) {
        this.rating = rating;
        return this;
    }

}
