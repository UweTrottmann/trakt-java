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
	private Boolean watched;
	
	public String getTmdbId() {
		return this.tmdbId;
	}
	public void setTmdbId(String tmdbId) {
		this.tmdbId = tmdbId;
	}
	public Integer getPlays() {
		return this.plays;
	}
	public void setPlays(Integer plays) {
		this.plays = plays;
	}
	public Boolean getInCollection() {
		return this.inCollection;
	}
	public void setInCollection(Boolean inCollection) {
		this.inCollection = inCollection;
	}
	public Date getReleased() {
		return this.released;
	}
	public void setReleased(Date released) {
		this.released = released;
	}
	public String getTrailer() {
		return this.trailer;
	}
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	public Integer getRuntime() {
		return this.runtime;
	}
	public void setRuntime(Integer runtime) {
		this.runtime = runtime;
	}
	public String getTagline() {
		return this.tagline;
	}
	public void setTagline(String tagline) {
		this.tagline = tagline;
	}
	public String getOverview() {
		return this.overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getCertification() {
		return this.certification;
	}
	public void setCertification(String certification) {
		this.certification = certification;
	}
	public Boolean getWatched() {
	    return this.watched;
	}
	public void setWatched(Boolean watched) {
		this.watched = watched;
	}
}
