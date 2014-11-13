package com.uwetrottmann.trakt.v2.entities;

import com.uwetrottmann.trakt.v2.enums.Rating;

public class Season {

    public Integer number;
    public SeasonIds ids;

    public Rating rating;
    public Integer episode_count;
    public Images images;

}
