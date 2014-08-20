package com.uwetrottmann.trakt.v2.entities;

import java.util.Date;
import java.util.List;

public class SyncWatchedSeason {

    public Date watched_at;
    public int number;
    public List<SyncWatchedEpisode> episodes;

}
