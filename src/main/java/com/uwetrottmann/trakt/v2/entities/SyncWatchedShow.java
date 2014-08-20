package com.uwetrottmann.trakt.v2.entities;

import java.util.Date;
import java.util.List;

public class SyncWatchedShow {

    public Date watched_at;
    public ShowIds ids;
    public List<SyncWatchedSeason> seasons;

}
