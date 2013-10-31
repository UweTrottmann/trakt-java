
package com.jakewharton.trakt.entities;

import com.google.gson.annotations.SerializedName;
import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.Gender;

import java.util.Calendar;
import java.util.List;

public class UserProfile implements TraktEntity {
    private static final long serialVersionUID = -4145012978937162733L;

    public static class Stats implements TraktEntity {
        private static final long serialVersionUID = -2737256634772977389L;

        public static class Shows implements TraktEntity {
            private static final long serialVersionUID = -2888630218268563052L;

            public Integer library;
        }

        public static class Episodes implements TraktEntity {
            private static final long serialVersionUID = 7210925664642958187L;

            public Integer watched;
            @SerializedName("watched_unique")
            public Integer watchedUnique;
            @SerializedName("watched_trakt")
            public Integer watchedTrakt;
            @SerializedName("watched_trakt_unique")
            public Integer watchedTraktUnique;
            @SerializedName("watched_elsewhere")
            public Integer watchedElsewhere;
            public Integer unwatched;

        }

        public static class Movies implements TraktEntity {
            private static final long serialVersionUID = 5061541628681754141L;

            public Integer watched;
            @SerializedName("watched_unique")
            public Integer watchedUnique;
            @SerializedName("watched_trakt")
            public Integer watchedTrakt;
            @SerializedName("watched_trakt_unique")
            public Integer watchedTraktUnique;
            @SerializedName("watched_elsewhere")
            public Integer watchedElsewhere;
            public Integer library;
            public Integer unwatched;

        }

        public Integer friends;
        public Shows shows;
        public Episodes episodes;
        public Movies movies;

    }

    public String username;
    @SerializedName("protected")
    public Boolean _protected;
    @SerializedName("full_name")
    public String fullName;
    public Gender gender;
    public Integer age;
    public String location;
    public String about;
    public Calendar joined;
    public String avatar;
    public String url;
    public boolean vip;
    public Calendar since;
    public Stats stats;
    public ActivityItemBase watching;
    public List<ActivityItem> watched;
    public Integer plays;
    public TvShowEpisode episode;
    public Calendar approved;

}
