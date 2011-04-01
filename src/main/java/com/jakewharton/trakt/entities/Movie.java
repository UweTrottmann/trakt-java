package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

public final class Movie implements TraktEntity {
	private static final long serialVersionUID = -1543214252495012419L;

	private String title;
	private Integer year;
	private String url;
	private String imdbId;
	private String tmdbId;
	private Integer plays;
	private Boolean inCollection;
	private Images images;
	
	public String getTitle() {
		return this.title;
	}
	public Integer getYear() {
		return this.year;
	}
	public String getUrl() {
		return this.url;
	}
	public String getImdbId() {
		return this.imdbId;
	}
	public String getTmdbId() {
		return this.tmdbId;
	}
	public Integer getPlays() {
		return this.plays;
	}
	public Boolean getInCollection() {
		return this.inCollection;
	}
	public Images getImages() {
		return this.images;
	}
}
