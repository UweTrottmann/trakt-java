package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

public class BaseEpisode {

    public Integer number;

    /** collection */
    public DateTime collected_at;
    /** watched */
    public Integer plays;
    /** progress */
    public Boolean completed;

}
