package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

public class List implements TraktEntity {
	private static final long serialVersionUID = -5768791212077534364L;

	private String name;
	private String slug;
	private String url;
	private String description;
	private String privacy;
	
	public String getName() {
		return this.name;
	}
	public String getSlug() {
		return this.slug;
	}
	public String getUrl() {
		return this.url;
	}
	public String getDescription() {
		return this.description;
	}
	public String getPrivacy() {
		return this.privacy;
	}
}
