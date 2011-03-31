package com.jakewharton.trakt.entities;

import java.util.Date;
import com.jakewharton.trakt.TraktEntity;

public final class TvShowEpisode implements TraktEntity {
	private static final long serialVersionUID = -1550739539663499211L;
	
	private Integer season;
	private Integer number;
	private String title;
	private String url;
	private Date firstAired;
	
	public Integer getSeason() {
		return season;
	}
	public Integer getNumber() {
		return number;
	}
	public String getTitle() {
		return title;
	}
	public String getUrl() {
		return url;
	}
	public Date getFirstAired() {
		return firstAired;
	}
}
