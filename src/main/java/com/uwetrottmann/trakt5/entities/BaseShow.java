package com.uwetrottmann.trakt5.entities;

import java.util.Date;
import java.util.List;

public class BaseShow {

    public Show show;

    /** collection, watched */
    public List<BaseSeason> seasons;

    /** collection */
    public Date last_collected_at;
    /** watchlist */
    public Date listed_at;
    /** watched */
    public Integer plays;
    public Date last_watched_at;
    /** progress */
    public Integer aired;
    public Integer completed;
    public List<Season> hidden_seasons;
    public Episode next_episode;

}
