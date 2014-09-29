package com.uwetrottmann.trakt.v2.entities;

import java.util.List;

public class SyncCollectedShow extends BaseCollectedEntity {

    public ShowIds ids;
    public List<CollectedSeason> seasons;

    public SyncCollectedShow(ShowIds ids) {
        this.ids = ids;
    }

}
