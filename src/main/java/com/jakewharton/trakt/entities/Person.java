package com.jakewharton.trakt.entities;

import java.util.Date;
import com.google.gson.annotations.SerializedName;
import com.jakewharton.trakt.TraktEntity;

public final class Person implements TraktEntity {
	private static final long serialVersionUID = -4755476212550445673L;
	
	private String name;
	private String url;
	private String biography;
	private Date birthday;
	private String birthplace;
	@SerializedName("tmdb_id") private Integer tmdbId;
	private Images images;
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBiography() {
		return this.biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	public Date getBirthday() {
		return this.birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getBirthplace() {
		return this.birthplace;
	}
	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}
	public Integer getTmdbId() {
		return this.tmdbId;
	}
	public void setTmdbId(Integer tmdbId) {
		this.tmdbId = tmdbId;
	}
	public Images getImages() {
		return this.images;
	}
	public void setImages(Images images) {
		this.images = images;
	}
}
