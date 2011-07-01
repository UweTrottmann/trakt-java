package com.jakewharton.trakt.entities;

import java.util.Date;
import com.google.gson.annotations.SerializedName;
import com.jakewharton.trakt.TraktEntity;

public final class Movie extends MediaBase implements TraktEntity {
	private static final long serialVersionUID = -1543214252495012419L;

	@SerializedName("tmdb_id") private String tmdbId;
	private Integer plays;
	@SerializedName("in_collection") private Boolean inCollection;
	private Date released;
	private String trailer;
	private Integer runtime;
	private String tagline;
	private String overview;
	private String certification; //TODO make enum
	
	public String getTmdbId() {
		return this.tmdbId;
	}
	public Integer getPlays() {
		return this.plays;
	}
	public Boolean getInCollection() {
		return this.inCollection;
	}
	public Date getReleased() {
		return this.released;
	}
	public String getTrailer() {
		return this.trailer;
	}
	public Integer getRuntime() {
		return this.runtime;
	}
	public String getTagline() {
		return this.tagline;
	}
	public String getOverview() {
		return this.overview;
	}
	public String getCertification() {
		return this.certification;
	}
}
