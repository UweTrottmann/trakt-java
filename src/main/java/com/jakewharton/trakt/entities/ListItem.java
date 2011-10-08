package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.ListItemType;

public class ListItem implements TraktEntity {
	private static final long serialVersionUID = 7584772036063464460L;

	public ListItemType type;
	public Movie movie;
	public TvShow show;
	public String season;
	public String episode_num;
	public TvShowEpisode episode;

	public ListItemType getType() {
		return type;
	}
	public Movie getMovie() {
		return movie;
	}
	public TvShow getShow() {
		return show;
	}
	public String getSeason() {
		return season;
	}
	public String getEpisodeNumber() {
		return episode_num;
	}
	public TvShowEpisode getEpisode() {
		return episode;
	}
}
