package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

public class Checkin implements TraktEntity {

    public String imdb_id;
    public Integer tvdb_id;
    public String title;
    public Integer year;
    public Integer season;
    public Integer episode;
    public Integer episode_tvdb_id;
    public Integer duration;
    public Integer venue_id;
    public String venue_name;
    public Share share;
    public String message;
    public String app_version;
    public String app_date;

}
