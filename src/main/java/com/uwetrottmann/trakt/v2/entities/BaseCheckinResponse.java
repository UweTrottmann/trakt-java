package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

public abstract class BaseCheckinResponse {

    public DateTime watched_at;
    public ShareSettings sharing;

}
