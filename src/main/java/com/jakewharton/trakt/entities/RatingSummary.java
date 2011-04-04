package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.Rating;

public class RatingSummary implements TraktEntity {
	private static final long serialVersionUID = 8424378149600617021L;
	
	public static class Ratings implements TraktEntity {
		private static final long serialVersionUID = -7517132370821535250L;
		
		private Integer percentage;
		private Integer votes;
		private Integer loved;
		private Integer hated;
		
		public Integer getPercentage() {
			return percentage;
		}
		public Integer getVotes() {
			return votes;
		}
		public Integer getLoved() {
			return loved;
		}
		public Integer getHated() {
			return hated;
		}
	}
	
	private String status; //TODO: enum
	private String message;
	private String type; //TODO: enum
	private Rating rating;
	private Ratings ratings;
	private Boolean facebook;
	private Boolean twitter;
	private Boolean tumblr;
	
	public String getStatus() {
		return this.status;
	}
	public String getMessage() {
		return this.message;
	}
	public String getType() {
		return this.type;
	}
	public Rating getRating() {
		return this.rating;
	}
	public Ratings getRatings() {
		return this.ratings;
	}
	public Boolean getFacebook() {
		return this.facebook;
	}
	public Boolean getTwitter() {
		return this.twitter;
	}
	public Boolean getTumblr() {
		return this.tumblr;
	}
}
