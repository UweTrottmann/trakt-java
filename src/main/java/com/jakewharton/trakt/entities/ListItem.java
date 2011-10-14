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
	public void setType(ListItemType type) {
		this.type = type;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public TvShow getShow() {
		return show;
	}
	public void setShow(TvShow show) {
		this.show = show;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getEpisodeNumber() {
		return episode_num;
	}
	public void setEpisodeNumber(String episodeNumber) {
		this.episode_num = episodeNumber;
	}
	public TvShowEpisode getEpisode() {
		return episode;
	}
	public void setEpisode(TvShowEpisode episode) {
		this.episode = episode;
	}
}
