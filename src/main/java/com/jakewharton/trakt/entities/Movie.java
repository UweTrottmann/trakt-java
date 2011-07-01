package com.jakewharton.trakt.entities;

import com.google.gson.annotations.SerializedName;
import com.jakewharton.trakt.TraktEntity;

public final class Movie extends MediaBase implements TraktEntity {
	private static final long serialVersionUID = -1543214252495012419L;

	@SerializedName("tmdb_id") private String tmdbId;
	private Integer plays;
	@SerializedName("in_collection") private Boolean inCollection;
	
	public String getTmdbId() {
		return this.tmdbId;
	}
	public Integer getPlays() {
		return this.plays;
	}
	public Boolean getInCollection() {
		return this.inCollection;
	}
}
