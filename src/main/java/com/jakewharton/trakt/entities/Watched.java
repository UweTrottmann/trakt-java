package com.jakewharton.trakt.entities;

import java.util.Date;
import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.MediaType;

public final class Watched implements TraktEntity {
	private static final long serialVersionUID = 4535846809492296227L;

	private MediaType type;
	private Date watched;
	private Movie movie;
	private TvShow show;
	private TvShowEpisode episode;
	
	public MediaType getType() {
		return type;
	}
	public Date getWatched() {
		return watched;
	}
	public Movie getMovie() {
		return movie;
	}
	public TvShow getShow() {
		return show;
	}
	public TvShowEpisode getEpisode() {
		return episode;
	}
}
