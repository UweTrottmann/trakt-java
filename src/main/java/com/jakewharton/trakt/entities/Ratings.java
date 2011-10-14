package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

public class Ratings implements TraktEntity {
	private static final long serialVersionUID = -7517132370821535250L;
	
	private Integer percentage;
	private Integer votes;
	private Integer loved;
	private Integer hated;
	
	public Integer getPercentage() {
		return percentage;
	}
	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}
	public Integer getVotes() {
		return votes;
	}
	public void setVotes(Integer votes) {
		this.votes = votes;
	}
	public Integer getLoved() {
		return loved;
	}
	public void setLoved(Integer loved) {
		this.loved = loved;
	}
	public Integer getHated() {
		return hated;
	}
	public void setHated(Integer hated) {
		this.hated = hated;
	}
}