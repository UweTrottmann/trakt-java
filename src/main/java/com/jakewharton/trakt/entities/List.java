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
	public void setName(String name) {
		this.name = name;
	}
	public String getSlug() {
		return this.slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ListPrivacy getPrivacy() {
		return this.privacy;
	}
	public void setPrivacy(ListPrivacy privacy) {
		this.privacy = privacy;
	}
	public java.util.List<ListItem> getItems() {
		return this.items;
	}
	public void setItems(java.util.List<ListItem> items) {
		this.items = items;
	}
}
