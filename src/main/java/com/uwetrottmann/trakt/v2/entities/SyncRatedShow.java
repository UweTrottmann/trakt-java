package com.uwetrottmann.trakt.v2.entities;

import java.util.List;

public class SyncRatedShow extends BaseRatedEntity {

    public ShowIds ids;
    public List<SyncRatedSeason> seasons;

}
