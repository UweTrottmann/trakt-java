package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

public class Movie extends BaseEntity {

    public Integer year;
    public MovieIds ids;

    // extended info
    public String tagline;
    public String overview;
    public DateTime released;
    public Integer runtime;
    public String language;

}
