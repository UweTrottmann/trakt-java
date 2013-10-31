package com.jakewharton.trakt.entities;

import com.google.gson.annotations.SerializedName;
import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.DayOfTheWeek;

import java.util.Date;
import java.util.List;

public class TvShow extends MediaBase implements TraktEntity {
    private static final long serialVersionUID = 862473930551420996L;

    public Date first_aired;
    public long first_aired_utc;
    public String country;
    public String overview;
    public Integer runtime;
    public String network;
    @SerializedName("air_day") public DayOfTheWeek airDay;
    @SerializedName("air_time") public String airTime;
    public String certification; //TODO: enum
    public Integer tvdb_id;
    public Integer tvrage_id;
    public List<TvShowEpisode> episodes;
    @SerializedName("top_episodes") public List<TvShowEpisode> topEpisodes;
    public List<TvShowSeason> seasons;

}
