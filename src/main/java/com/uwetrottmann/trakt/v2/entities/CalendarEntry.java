package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

/**
 * Calendar entry with either {@code airs_at, episode, show} filled or only {@code movie}.
 */
public class CalendarEntry {

    public DateTime airs_at;
    public Episode episode;
    public Show show;
    public Movie movie;

}
