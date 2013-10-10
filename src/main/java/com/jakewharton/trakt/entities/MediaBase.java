package com.jakewharton.trakt.entities;

import com.google.gson.annotations.SerializedName;
import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.Rating;

import java.util.List;

public abstract class MediaBase implements TraktEntity {
    private static final long serialVersionUID = 753880113366868498L;

    public static class Stats implements TraktEntity {
        private static final long serialVersionUID = -5436127125832664020L;

        public Integer watchers;
        public Integer plays;

        /** @deprecated Use {@link #watchers} */
        @Deprecated
        public Integer getWatchers() {
            return this.watchers;
        }
        /** @deprecated Use {@link #plays} */
        @Deprecated
        public Integer getPlays() {
            return this.plays;
        }
    }

    public String title;
    public Integer year;
    public String url;
    public Images images;
    @SerializedName("top_watchers") public List<UserProfile> topWatchers;
    public Ratings ratings;
    public Stats stats;
    public String imdb_id;
    public Rating rating;
    public Rating rating_advanced;
    @SerializedName("in_watchlist") public Boolean inWatchlist;

}
