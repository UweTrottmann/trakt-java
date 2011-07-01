package com.jakewharton.trakt.entities;

import java.util.List;
import com.jakewharton.trakt.TraktEntity;

public class TvShowSeason implements TraktEntity {
	private static final long serialVersionUID = -1283154821327471366L;
	
	public static final class Episodes implements TraktEntity {
		private static final long serialVersionUID = -8143500365188820979L;
		
		private Integer count;
		private List<Integer> list;
		
		public Integer getCount() {
			return this.count;
		}
		public List<Integer> getList() {
			return this.list;
		}
	}

	private Integer season;
	private Episodes episodes;
	private String url;
	private Images images;
	
	public Integer getSeason() {
		return this.season;
	}
	public Episodes getEpisodes() {
		return this.episodes;
	}
	public String getUrl() {
		return this.url;
	}
	public Images getImages() {
		return this.images;
	}
}
