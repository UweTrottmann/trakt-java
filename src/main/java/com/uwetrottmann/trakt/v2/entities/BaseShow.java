package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

import java.util.List;

public class BaseShow {

    public Show show;
    public List<BaseSeason> seasons;

    public DateTime collected_at;
    public int plays;

}
