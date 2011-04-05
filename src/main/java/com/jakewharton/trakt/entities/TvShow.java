package com.jakewharton.trakt.entities;

import java.util.Date;
import java.util.List;
import com.jakewharton.trakt.TraktEntity;

public final class TvShow implements TraktEntity {
	private static final long serialVersionUID = 862473930551420996L;

	private String title;
	private Integer year;
	private String url;
	private Date firstAired;
	private String country;
	private String overview;
	private Integer runtime;
	private String network;
	private String airDay; //TODO: enum
	private String airTime;
	private String certification; //TODO: enum
	private String imdbId;
	private String tvdbId;
	private String tvrageId;
	private Images images;
	private List<TvShowEpisode> episodes;
	private List<UserProfile> topWatchers;
	private List<TvShowEpisode> topEpisodes;
	private Ratings ratings;
	private Stats stats;
	
	public String getTitle() {
		return this.title;
	}
	public Integer getYear() {
		return this.year;
	}
	public String getUrl() {
		return this.url;
	}
	public Date getFirstAired() {
		return this.firstAired;
	}
	public String getCountry() {
		return this.country;
	}
	public String getOverview() {
		return this.overview;
	}
	public Integer getRuntime() {
		return this.runtime;
	}
	public String getNetwork() {
		return this.network;
	}
	public String getAirDay() {
		return this.airDay;
	}
	public String getAirTime() {
		return this.airTime;
	}
	public String getCertification() {
		return this.certification;
	}
	public String getImdbId() {
		return this.imdbId;
	}
	public String getTvdbId() {
		return this.tvdbId;
	}
	public String getTvrageId() {
		return this.tvrageId;
	}
	public Images getImages() {
		return this.images;
	}
	public List<TvShowEpisode> getEpisodes() {
		return this.episodes;
	}
	public List<UserProfile> getTopWatchers() {
		return this.topWatchers;
	}
	public List<TvShowEpisode> getTopEpisodes() {
		return this.topEpisodes;
	}
	public Ratings getRatings() {
		return this.ratings;
	}
	public Stats getStats() {
		return this.stats;
	}
}
