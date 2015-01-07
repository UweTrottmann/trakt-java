package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

import java.util.List;

public class BaseShow {

    public Show show;

    /** collection, watched */
    public List<BaseSeason> seasons;

    /** collection */
    public DateTime collected_at;
    /** watchlist */
    public DateTime listed_at;
    /** watched */
    public Integer plays;
    /** progress */
    public Integer aired;
    /** progress */
    public Integer completed;
    /** progress */
    public Episode next_episode;

}
