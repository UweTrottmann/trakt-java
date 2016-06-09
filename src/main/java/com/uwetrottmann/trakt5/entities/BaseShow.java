package com.uwetrottmann.trakt5.entities;

import org.joda.time.DateTime;

import java.util.List;

public class BaseShow {

    public Show show;

    /** collection, watched */
    public List<BaseSeason> seasons;

    /** collection */
    public DateTime last_collected_at;
    /** watchlist */
    public DateTime listed_at;
    /** watched */
    public Integer plays;
    public DateTime last_watched_at;
    /** progress */
    public Integer aired;
    public Integer completed;
    public List<Season> hidden_seasons;
    public Episode next_episode;

}
