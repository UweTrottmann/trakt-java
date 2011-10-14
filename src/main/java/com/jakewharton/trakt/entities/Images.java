package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

public final class Images implements TraktEntity {
	private static final long serialVersionUID = -4374523954772900340L;
	
	private String poster;
	private String fanart;
	private String headshot;
	private String screen;
	
	public String getPoster() {
		return this.poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getFanart() {
		return this.fanart;
	}
	public void setFanart(String fanart) {
		this.fanart = fanart;
	}
	public String getHeadshot() {
		return this.headshot;
	}
	public void setHeadshot(String headshot) {
		this.headshot = headshot;
	}
	public String getScreen() {
		return this.screen;
	}
	public void setScreen(String screen) {
		this.screen = screen;
	}
}