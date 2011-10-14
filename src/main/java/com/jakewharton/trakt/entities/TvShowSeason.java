package com.jakewharton.trakt.entities;

import java.util.List;
import com.jakewharton.trakt.TraktEntity;

public class TvShowSeason implements TraktEntity {
	private static final long serialVersionUID = -1283154821327471366L;
	
	public static final class Episodes implements TraktEntity {
		private static final long serialVersionUID = -8143500365188820979L;
		
		private Integer count;
		private List<Integer> numbers;
		private List<TvShowEpisode> episodes;
		
		public Integer getCount() {
			return this.count;
		}
		public void setCount(Integer count) {
			this.count = count;
		}
		public List<Integer> getNumbers() {
			return this.numbers;
		}
		public void setNumbers(List<Integer> numbers) {
			this.numbers = numbers;
		}
		public List<TvShowEpisode> getEpisodes() {
			return this.episodes;
		}
		public void setEpisodes(List<TvShowEpisode> episodes) {
			this.episodes = episodes;
		}
	}

	private Integer season;
	private Episodes episodes;
	private String url;
	private Images images;
	
	public Integer getSeason() {
		return this.season;
	}
	public void setSeason(Integer season) {
		this.season = season;
	}
	public Episodes getEpisodes() {
		return this.episodes;
	}
	public void setEpisodes(Episodes episodes) {
		this.episodes = episodes;
	}
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Images getImages() {
		return this.images;
	}
	public void setImages(Images images) {
		this.images = images;
	}
}
