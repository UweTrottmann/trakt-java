package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.OffsetDateTime;

public class PlaybackResponse extends GenericProgress {

    public Long id;
    /** Playpack response only. */
    public OffsetDateTime paused_at;
    /** Playpack response only. */
    public String type;
    /** Scrobble response only. */
    public String action;
    /** Scrobble response only. */
    public ShareSettings sharing;

}
