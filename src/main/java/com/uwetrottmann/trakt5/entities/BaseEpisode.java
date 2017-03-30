package com.uwetrottmann.trakt5.entities;

import java.util.Date;

public class BaseEpisode {

    public Integer number;

    /** collection */
    public Date collected_at;
    /** watched */
    public Integer plays;
    public Date last_watched_at;
    /** progress */
    public Boolean completed;

}
