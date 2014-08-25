package com.uwetrottmann.trakt.v2.entities;

import java.util.Date;

public class Movie extends BaseEntity {

    public Integer year;
    public MovieIds ids;

    // extended info
    public String tagline;
    public String overview;
    public Date released;
    public Integer runtime;
    public String language;

}
