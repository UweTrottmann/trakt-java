package com.jakewharton.trakt.entities;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.Rating;

public abstract class MediaBase implements TraktEntity {
	private static final long serialVersionUID = 753880113366868498L;

	public static class Stats implements TraktEntity {
		private static final long serialVersionUID = -5436127125832664020L;

		private Integer watchers;
		private Integer plays;

		public Integer getWatchers() {
			return this.watchers;
		}
		public void setWatchers(Integer watchers) {
			this.watchers = watchers;
		}
		public Integer getPlays() {
			return this.plays;
		}
		public void setPlays(Integer plays) {
			this.plays = plays;
		}
	}

	private String title;
	private Integer year;
	private String url;
	private Images images;
	@SerializedName("top_watchers") private List<UserProfile> topWatchers;
	private Ratings ratings;
	private Stats stats;
	@SerializedName("imdb_id") private String imdbId;
	private Rating rating;
	@SerializedName("in_watchlist") private Boolean inWatchlist;

	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getYear() {
		return this.year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Images getImages() {
		return this.images;
	}
	public void setImages(Images images) {
		this.images = images;
	}
	public List<UserProfile> getTopWatchers() {
		return this.topWatchers;
	}
	public void setTopWatchers(List<UserProfile> topWatchers) {
		this.topWatchers = topWatchers;
	}
	public Ratings getRatings() {
		return this.ratings;
	}
	public void setRatings(Ratings ratings) {
		this.ratings = ratings;
	}
	public Stats getStats() {
		return this.stats;
	}
	public void setStats(Stats stats) {
		this.stats = stats;
	}
	public String getImdbId() {
		return this.imdbId;
	}
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}
	public Rating getRating() {
		return this.rating;
	}
	public void setRating(Rating rating) {
		this.rating = rating;
	}
	public Boolean getInWatchlist() {
		return this.inWatchlist;
	}
	public void setInWatchList(Boolean inWatchlist) {
		this.inWatchlist = inWatchlist;
	}
}
