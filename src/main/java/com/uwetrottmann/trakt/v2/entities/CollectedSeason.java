package com.uwetrottmann.trakt.v2.entities;

import java.util.List;

public class CollectedSeason {

    public Integer number;
    public List<CollectedEpisode> episodes;

    public CollectedSeason(int number) {
        this.number = number;
    }

}
