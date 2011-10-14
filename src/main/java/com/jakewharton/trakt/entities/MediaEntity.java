package com.jakewharton.trakt.entities;

import java.util.Calendar;
import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.MediaType;

public class MediaEntity implements TraktEntity {
	private static final long serialVersionUID = 4535846809492296227L;

	private MediaType type;
	private Calendar watched;
	private Calendar date;
	private Movie movie;
	private TvShow show;
	private TvShowEpisode episode;
	
	public MediaType getType() {
		return this.type;
	}
	public void setType(MediaType type) {
		this.type = type;
	}
	public Calendar getWatched() {
		return this.watched;
	}
	public void setWatched(Calendar watched) {
		this.watched = watched;
	}
	public Calendar getDate() {
		return this.date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public Movie getMovie() {
		return this.movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public TvShow getShow() {
		return this.show;
	}
	public void setShow(TvShow show) {
		this.show = show;
	}
	public TvShowEpisode getEpisode() {
		return this.episode;
	}
	public void setEpisode(TvShowEpisode episode) {
		this.episode = episode;
	}
}
