package com.jakewharton.trakt.entities;

import java.util.Date;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.DayOfTheWeek;

public final class TvShow extends MediaBase implements TraktEntity {
	private static final long serialVersionUID = 862473930551420996L;

	@SerializedName("first_aired") private Date firstAired;
	private String country;
	private String overview;
	private Integer runtime;
	private String network;
	@SerializedName("air_day") private DayOfTheWeek airDay;
	@SerializedName("air_time") private String airTime;
	private String certification; //TODO: enum
	@SerializedName("tvdb_id") private String tvdbId;
	@SerializedName("tvrage_id") private String tvrageId;
	private List<TvShowEpisode> episodes;
	@SerializedName("top_episodes") private List<TvShowEpisode> topEpisodes;
	private List<TvShowSeason> seasons;
	
	public Date getFirstAired() {
		return this.firstAired;
	}
	public void setFirstAired(Date firstAired) {
		this.firstAired = firstAired;
	}
	public String getCountry() {
		return this.country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getOverview() {
		return this.overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public Integer getRuntime() {
		return this.runtime;
	}
	public void setRuntime(Integer runtime) {
		this.runtime = runtime;
	}
	public String getNetwork() {
		return this.network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public DayOfTheWeek getAirDay() {
		return this.airDay;
	}
	public void setAirDay(DayOfTheWeek airDay) {
		this.airDay = airDay;
	}
	public String getAirTime() {
		return this.airTime;
	}
	public void setAirTime(String airTime) {
		this.airTime = airTime;
	}
	public String getCertification() {
		return this.certification;
	}
	public void setCertification(String certification) {
		this.certification = certification;
	}
	public String getTvdbId() {
		return this.tvdbId;
	}
	public void setTvdbId(String tvdbId) {
		this.tvdbId = tvdbId;
	}
	public String getTvRageId() {
		return this.tvrageId;
	}
	public void setTvRageId(String tvrageId) {
		this.tvrageId = tvrageId;
	}
	public List<TvShowEpisode> getEpisodes() {
		return this.episodes;
	}
	public void setEpisodes(List<TvShowEpisode> episodes) {
		this.episodes = episodes;
	}
	public List<TvShowEpisode> getTopEpisodes() {
		return this.topEpisodes;
	}
	public void setTopEpisodes(List<TvShowEpisode> topEpisodes) {
		this.topEpisodes = topEpisodes;
	}
	public List<TvShowSeason> getSeasons() {
		return this.seasons;
	}
	public void setSeasons(List<TvShowSeason> seasons) {
		this.seasons = seasons;
	}
}
