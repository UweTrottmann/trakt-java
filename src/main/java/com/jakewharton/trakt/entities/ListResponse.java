package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.enumerations.ListPrivacy;

public class ListResponse extends Response {
	private static final long serialVersionUID = 5368378936105337182L;

	private String name;
	private String slug;
	private ListPrivacy privacy;
	
	public String getName() {
		return this.name;
	}
	public String getSlug() {
		return this.slug;
	}
	public ListPrivacy getPrivacy() {
		return this.privacy;
	}
}
