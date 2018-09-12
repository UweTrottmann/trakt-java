package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.OffsetDateTime;

import java.util.List;

public class Season {

    public Integer number;
    public SeasonIds ids;

    public String title;
    public String overview;
    public String network;
    public OffsetDateTime first_aired;
    public Double rating;
    public Integer votes;
    public Integer episode_count;
    public Integer aired_episodes;
    public List<Episode> episodes;
}
