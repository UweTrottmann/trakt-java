package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

public class LastActivities {

    public DateTime all;
    public LastActivityMore movies;
    public LastActivityMore episodes;
    public LastActivity shows;
    public LastActivity seasons;

}
