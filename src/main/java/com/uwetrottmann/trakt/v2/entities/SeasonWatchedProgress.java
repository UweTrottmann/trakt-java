package com.uwetrottmann.trakt.v2.entities;

import java.util.List;

public class SeasonWatchedProgress {

    public int number;
    public int aired;
    public int completed;

    public List<EpisodeWatched> episodes;
}
