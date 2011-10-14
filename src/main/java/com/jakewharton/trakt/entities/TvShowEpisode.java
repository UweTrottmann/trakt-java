package com.jakewharton.trakt.entities;

import java.util.Calendar;
import java.util.Date;
import com.google.gson.annotations.SerializedName;
import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.Rating;

public final class TvShowEpisode implements TraktEntity {
	private static final long serialVersionUID = -1550739539663499211L;
	
	private Integer season;
	private Integer number;
	private String title;
	private String overview;
	private String url;
	@SerializedName("first_aired") private Date firstAired;
	private Calendar inserted;
	private Integer plays;
	private Images images;
	private Ratings ratings;
	private Boolean watched;
	private Rating rating;
	@SerializedName("in_watchlist") private Boolean inWatchlist;
	
	public Integer getSeason() {
		return this.season;
	}
	public void setSeason(Integer season) {
		this.season = season;
	}
	public Integer getNumber() {
		return this.number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOverview() {
		return this.overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getFirstAired() {
		return this.firstAired;
	}
	public void setFirstAired(Date firstAired) {
		this.firstAired = firstAired;
	}
	public Calendar getInserted() {
		return this.inserted;
	}
	public void setInserted(Calendar inserted) {
		this.inserted = inserted;
	}
	public Integer getPlays() {
		return this.plays;
	}
	public void setPlays(Integer plays) {
		this.plays = plays;
	}
	public Images getImages() {
		return this.images;
	}
	public void setImages(Images images) {
		this.images = images;
	}
	public Ratings getRatings() {
		return this.ratings;
	}
	public void setRatings(Ratings ratings) {
		this.ratings = ratings;
	}
	public Boolean getWatched() {
		return this.watched;
	}
	public void setWatched(Boolean watched) {
		this.watched = watched;
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
	public void setInWatchlist(Boolean inWatchlist) {
		this.inWatchlist = inWatchlist;
	}
}
