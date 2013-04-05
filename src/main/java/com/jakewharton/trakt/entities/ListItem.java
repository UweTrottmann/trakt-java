package com.jakewharton.trakt.entities;

import com.google.gson.annotations.SerializedName;
import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.ListItemType;

public class ListItem implements TraktEntity {
    private static final long serialVersionUID = 7584772036063464460L;

    public ListItemType type;
    public Movie movie;
    public TvShow show;
    public String season;
    @SerializedName("episode_num") public String episodeNumber;
    public TvShowEpisode episode;

}
