package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

public class Stats implements TraktEntity {
	private static final long serialVersionUID = -5436127125832664020L;
	
	private Integer watchers;
	private Integer plays;
	
	public Integer getWatchers() {
		return this.watchers;
	}
	public Integer getPlays() {
		return this.plays;
	}
}
