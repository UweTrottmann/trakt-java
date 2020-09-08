package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.OffsetDateTime;

public class BaseEpisode {

    public Integer number;

    /** collection */
    public OffsetDateTime collected_at;
    /** watched */
    public Integer plays;
    public OffsetDateTime last_watched_at;
    /** progress */
    public Boolean completed;

    public Metadata metadata;
}
