package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

public class Episode extends BaseEntity {

    public Integer season;
    public Integer number;
    public EpisodeIds ids;

    // extended info
    public String overview;
    public DateTime first_aired;
}
