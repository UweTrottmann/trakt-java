package com.uwetrottmann.trakt5.entities;

import org.joda.time.DateTime;

public class HistoryEntry {

    public Long id;
    public DateTime watched_at;
    public String action;
    public String type;

    public Episode episode;
    public Show show;

    public Movie movie;

}
