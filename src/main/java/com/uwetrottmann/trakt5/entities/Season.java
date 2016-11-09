package com.uwetrottmann.trakt5.entities;

import java.util.List;

public class Season {

    public Integer number;
    public SeasonIds ids;

    public String overview;
    public Double rating;
    public Integer votes;
    public Integer episode_count;
    public Integer aired_episodes;
    public List<Episode> episodes;
}
