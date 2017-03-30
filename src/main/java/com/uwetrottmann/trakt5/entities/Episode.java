package com.uwetrottmann.trakt5.entities;

import java.util.Date;

public class Episode extends BaseEntity {

    public Integer season;
    public Integer number;
    public EpisodeIds ids;

    // extended info
    public Integer number_abs;
    public Date first_aired;

}
