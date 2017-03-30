package com.uwetrottmann.trakt5.entities;

import java.util.Date;

public class HistoryEntry {

    public Long id;
    public Date watched_at;
    public String action;
    public String type;

    public Episode episode;
    public Show show;

    public Movie movie;

}
