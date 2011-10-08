package com.jakewharton.trakt.entities;

import com.google.gson.JsonArray;

public class ListItemsResponse extends Response {
	private static final long serialVersionUID = 8123553856114248596L;

	private Integer inserted;
	private Integer already_exist;
	private Integer skipped;
	private JsonArray skipped_array;
	
	public Integer getInserted() {
		return this.inserted;
	}
	public Integer getAlreadyExist() {
		return this.already_exist;
	}
	public Integer getSkipped() {
		return this.skipped;
	}
	public JsonArray getSkippedArray() {
		return this.skipped_array;
	}
}
