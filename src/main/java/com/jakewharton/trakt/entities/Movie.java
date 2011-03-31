package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

public final class Movie implements TraktEntity {
	private static final long serialVersionUID = -1543214252495012419L;

	private String title;
	private Integer year;
	private String url;
	private String imdbId;
	private String tmdbId;
	
	public String getTitle() {
		return title;
	}
	public Integer getYear() {
		return year;
	}
	public String getUrl() {
		return url;
	}
	public String getImdbId() {
		return imdbId;
	}
	public String getTmdbId() {
		return tmdbId;
	}
}
