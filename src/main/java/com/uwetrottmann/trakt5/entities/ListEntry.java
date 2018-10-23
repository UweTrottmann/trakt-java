package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.OffsetDateTime;

public class ListEntry {

    public Long id;
    public Integer rank;
    public OffsetDateTime listed_at;
    public String type;
    public Movie movie;
    public Show show;
    public Episode episode;
    public Person person;

}
