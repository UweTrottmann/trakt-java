package com.jakewharton.trakt.entities;

import java.util.Date;
import java.util.List;
import com.jakewharton.trakt.TraktEntity;

public final class CalendarDate implements TraktEntity {
	private static final long serialVersionUID = 5985118362541597172L;
	
	public static final class CalendarTvShowEpisode implements TraktEntity {
		private static final long serialVersionUID = -7066863350641449761L;
		
		private TvShow show;
		private TvShowEpisode episode;
		
		public TvShow getShow() {
			return this.show;
		}
		public void setShow(TvShow show) {
			this.show = show;
		}
		public TvShowEpisode getEpisode() {
			return this.episode;
		}
		public void setEpisode(TvShowEpisode episode) {
			this.episode = episode;
		}
	}
	
	private Date date;
	private List<CalendarTvShowEpisode> episodes;
	
	public Date getDate() {
		return this.date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<CalendarTvShowEpisode> getEpisodes() {
		return this.episodes;
	}
	public void setEpisodes(List<CalendarTvShowEpisode> episodes) {
		this.episodes = episodes;
	}
}
