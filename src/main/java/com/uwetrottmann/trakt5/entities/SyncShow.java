package com.uwetrottmann.trakt5.entities;

import com.uwetrottmann.trakt5.enums.Rating;
import org.threeten.bp.OffsetDateTime;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SyncShow {

    public ShowIds ids;
    public List<SyncSeason> seasons;

    public OffsetDateTime collected_at;
    public OffsetDateTime watched_at;
    public OffsetDateTime rated_at;
    public Rating rating;

    @Nonnull
    public SyncShow id(ShowIds id) {
        this.ids = id;
        return this;
    }

    @Nonnull
    public SyncShow seasons(List<SyncSeason> seasons) {
        this.seasons = seasons;
        return this;
    }

    @Nonnull
    public SyncShow seasons(SyncSeason season) {
        ArrayList<SyncSeason> list = new ArrayList<>(1);
        list.add(season);
        return seasons(list);
    }

    @Nonnull
    public SyncShow collectedAt(OffsetDateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    @Nonnull
    public SyncShow watchedAt(OffsetDateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

    @Nonnull
    public SyncShow ratedAt(OffsetDateTime ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    @Nonnull
    public SyncShow rating(Rating rating) {
        this.rating = rating;
        return this;
    }

}
