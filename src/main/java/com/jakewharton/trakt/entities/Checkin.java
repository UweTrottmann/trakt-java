package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

public class Checkin implements TraktEntity {

    public String imdb_id;
    public int tvdb_id;
    public String title;
    public int year;
    public int season;
    public int episode;
    public int duration;
    public String app_version;
    public String app_date;
    public int venue_id;
    public Share share;
    public String message;

}
