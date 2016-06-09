package com.uwetrottmann.trakt5.entities;

import org.joda.time.DateTime;

public class BaseEpisode {

    public Integer number;

    /** collection */
    public DateTime collected_at;
    /** watched */
    public Integer plays;
    public DateTime last_watched_at;
    /** progress */
    public Boolean completed;

}
