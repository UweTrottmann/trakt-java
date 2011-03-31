package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

public final class TvShow implements TraktEntity {
	private static final long serialVersionUID = 862473930551420996L;

	private String title;
	private String url;
	private String imdbId;
	private String tvdbId;
	private String tvrageId;
	
	public String getTitle() {
		return title;
	}
	public String getUrl() {
		return url;
	}
	public String getImdbId() {
		return imdbId;
	}
	public String getTvdbId() {
		return tvdbId;
	}
	public String getTvrageId() {
		return tvrageId;
	}
}
