package com.uwetrottmann.trakt5.entities;

import com.uwetrottmann.trakt5.enums.Rating;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SyncShow {

    public ShowIds ids;
    public List<SyncSeason> seasons;

    public Date collected_at;
    public Date watched_at;
    public Date rated_at;
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

    public SyncShow collectedAt(Date collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    public SyncShow watchedAt(Date watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

    public SyncShow ratedAt(Date ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    public SyncShow rating(Rating rating) {
        this.rating = rating;
        return this;
    }

}
