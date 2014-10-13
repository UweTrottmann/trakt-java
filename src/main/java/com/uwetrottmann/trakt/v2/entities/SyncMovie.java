package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

public class SyncMovie {

    public MovieIds ids;

    public DateTime collected_at;
    public DateTime watched_at;

}
