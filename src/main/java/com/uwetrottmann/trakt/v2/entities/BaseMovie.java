package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

public class BaseMovie {

    public Movie movie;

    public DateTime collected_at;
    public DateTime listed_at;
    public int plays;

}
