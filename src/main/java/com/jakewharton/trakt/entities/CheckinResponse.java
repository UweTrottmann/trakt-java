package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

import java.util.Date;

public class CheckinResponse extends Response {

    public static class Timestamps implements TraktEntity {
        public Date start;
        public Date end;
        public int active_for;
    }

    public Timestamps timestamps;
    public boolean facebook;
    public boolean twitter;
    public boolean tumblr;
    public boolean path;

}
