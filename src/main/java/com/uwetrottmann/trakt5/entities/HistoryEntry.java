package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.OffsetDateTime;

public class HistoryEntry {

    public Long id;
    public OffsetDateTime watched_at;
    public String action;
    public String type;

    public Episode episode;
    public Show show;

    public Movie movie;

}
