package com.uwetrottmann.trakt.v2.entities;

import java.util.List;

public class SyncRatedSeason extends BaseRatedEntity {

    public int number;
    public List<SyncRatedEpisode> episodes;

}
