package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.OffsetDateTime;

public class ListEntry {

    public OffsetDateTime listed_at;
    public Movie movie;
    public Show show;
    public Episode episode;
    public Person person;

}
