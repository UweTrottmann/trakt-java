package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.OffsetDateTime;

public class LastActivityMore extends LastActivity {

    public OffsetDateTime watched_at;
    public OffsetDateTime collected_at;
    public OffsetDateTime paused_at;
    public OffsetDateTime hidden_at;

}
