package com.uwetrottmann.trakt5.entities;

import org.joda.time.DateTime;

public class Episode extends BaseEntity {

    public Integer season;
    public Integer number;
    public EpisodeIds ids;

    // extended info
    public Integer number_abs;
    public DateTime first_aired;

}
