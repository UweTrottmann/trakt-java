package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.OffsetDateTime;

public class LastActivities {

    public OffsetDateTime all;
    public LastActivityMore movies;
    public LastActivityMore episodes;
    public LastActivity shows;
    public LastActivity seasons;
    public ListsLastActivity lists;

}
