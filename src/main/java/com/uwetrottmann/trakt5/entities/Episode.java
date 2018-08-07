package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.OffsetDateTime;

public class Episode extends BaseEntity {

    public Integer season;
    public Integer number;
    public EpisodeIds ids;

    // extended info
    public Integer number_abs;
    public OffsetDateTime first_aired;
    public Integer comment_count;
    public Integer runtime;
    

}
