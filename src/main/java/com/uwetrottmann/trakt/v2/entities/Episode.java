package com.uwetrottmann.trakt.v2.entities;

import java.util.Date;

public class Episode extends BaseEntity {

    public Integer season;
    public Integer number;
    public EpisodeIds ids;

    // extended info
    public String overview;
    public Date first_aired;
}
