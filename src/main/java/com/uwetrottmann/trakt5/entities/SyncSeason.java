package com.uwetrottmann.trakt5.entities;

import com.uwetrottmann.trakt5.enums.Rating;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class SyncSeason {

    public Integer number;
    public List<SyncEpisode> episodes;

    public DateTime collected_at;
    public DateTime watched_at;
    public DateTime rated_at;
    public Rating rating;

    public SyncSeason number(int number) {
        this.number = number;
        return this;
    }

    public SyncSeason episodes(List<SyncEpisode> episodes) {
        this.episodes = episodes;
        return this;
    }

    public SyncSeason episodes(SyncEpisode episode) {
        ArrayList<SyncEpisode> list = new ArrayList<>(1);
        list.add(episode);
        return episodes(list);
    }

    public SyncSeason collectedAt(DateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    public SyncSeason watchedAt(DateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

    public SyncSeason ratedAt(DateTime ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    public SyncSeason rating(Rating rating) {
        this.rating = rating;
        return this;
    }

}
