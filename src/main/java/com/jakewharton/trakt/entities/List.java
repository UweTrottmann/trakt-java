package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.ListPrivacy;

public class List implements TraktEntity {
	private static final long serialVersionUID = -5768791212077534364L;

	private String name;
	private String slug;
	private String url;
	private String description;
	private ListPrivacy privacy;
	private java.util.List<ListItem> items;
	
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
	public ListPrivacy getPrivacy() {
		return this.privacy;
	}
	public java.util.List<ListItem> getItems() {
		return this.items;
	}
}
