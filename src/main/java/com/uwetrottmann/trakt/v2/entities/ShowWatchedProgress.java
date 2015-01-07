package com.uwetrottmann.trakt.v2.entities;

import java.util.List;

public class ShowWatchedProgress {

    public int aired;
    public int completed;
    public List<SeasonWatchedProgress> seasons;

    public Episode next_episode;

}
