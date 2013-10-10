package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

import java.util.Date;

public class CheckinResponse extends Response {

    public static class Timestamps implements TraktEntity {
        public Date start;
        public Date end;
        public int active_for;
    }

    public static class Show implements TraktEntity {
        public String title;
        public int year;
        public String imdb_id;
        public int tvdb_id;
    }

    public Timestamps timestamps;
    public Show show;
    public boolean facebook;
    public boolean twitter;
    public boolean tumblr;
    public boolean path;

}
