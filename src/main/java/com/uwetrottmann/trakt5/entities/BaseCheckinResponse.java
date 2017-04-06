package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.OffsetDateTime;


public abstract class BaseCheckinResponse {

    public OffsetDateTime watched_at;
    public ShareSettings sharing;

}
