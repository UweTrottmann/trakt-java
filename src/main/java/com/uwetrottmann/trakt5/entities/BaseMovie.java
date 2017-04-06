package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.OffsetDateTime;

public class BaseMovie {

    public Movie movie;

    public OffsetDateTime collected_at;
    public OffsetDateTime last_watched_at;
    public OffsetDateTime listed_at;
    public int plays;

}
