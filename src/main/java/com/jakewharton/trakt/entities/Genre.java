package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

public final class Genre implements TraktEntity {
	private static final long serialVersionUID = -7818541411651542895L;

	private String name;
	private String slug;

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSlug() {
		return this.slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
}
