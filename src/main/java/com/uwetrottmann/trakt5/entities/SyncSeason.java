package com.uwetrottmann.trakt5.entities;

import com.uwetrottmann.trakt5.enums.Rating;
import org.threeten.bp.OffsetDateTime;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SyncSeason {

    public Integer number;
    public List<SyncEpisode> episodes;

    public OffsetDateTime collected_at;
    public OffsetDateTime watched_at;
    public OffsetDateTime rated_at;
    public Rating rating;

    @Nonnull
    public SyncSeason number(int number) {
        this.number = number;
        return this;
    }

    @Nonnull
    public SyncSeason episodes(List<SyncEpisode> episodes) {
        this.episodes = episodes;
        return this;
    }

    @Nonnull
    public SyncSeason episodes(SyncEpisode episode) {
        ArrayList<SyncEpisode> list = new ArrayList<>(1);
        list.add(episode);
        return episodes(list);
    }

    @Nonnull
    public SyncSeason collectedAt(OffsetDateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    @Nonnull
    public SyncSeason watchedAt(OffsetDateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

    @Nonnull
    public SyncSeason ratedAt(OffsetDateTime ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    @Nonnull
    public SyncSeason rating(Rating rating) {
        this.rating = rating;
        return this;
    }

}
