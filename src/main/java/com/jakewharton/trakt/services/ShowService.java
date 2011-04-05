package com.jakewharton.trakt.services;

import java.util.List;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.trakt.TraktApiBuilder;
import com.jakewharton.trakt.TraktApiService;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.entities.TvShow;
import com.jakewharton.trakt.entities.TvShowEpisode;
import com.jakewharton.trakt.entities.TvShowSeason;
import com.jakewharton.trakt.entities.UserProfile;

public class ShowService extends TraktApiService {
	/**
	 * <p>Notify Trakt that a user has stopped watching a show.</p>
	 * 
	 * <p><em>Warning</em>: This method requires a developer API key.</p>
	 * 
	 * @return Builder instance.
	 */
	public CancelWatchingBuilder cancelWatching() {
		return new CancelWatchingBuilder(this);
	}
	
	public static final class CancelWatchingBuilder extends TraktApiBuilder<Response> {
		private static final String URI = "/show/cancelwatching/{" + FIELD_API_KEY + "}";
		
		private CancelWatchingBuilder(ShowService service) {
			super(service, new TypeToken<Response>() {}, URI, HttpMethod.Post);
		}
	}
	
	/**
	 * Returns a array of all users watching a show.
	 * 
	 * @param query Either the slug (i.e. the-walking-dead) or TVDB ID. You can
	 * get a show's slug by browsing the website and looking at the URL when on
	 * a show summary page.
	 * @return Builder instance.
	 */
	public WatchingNowBuilder watchingNow(String query) {
		return new WatchingNowBuilder(this, query);
	}
	
	public static final class WatchingNowBuilder extends TraktApiBuilder<List<UserProfile>> {
		private static final String URI = "/show/watchingnow.json/{" + FIELD_API_KEY + "}/{" + FIELD_QUERY + "}/{" + FIELD_SEASON + "}/{" + FIELD_EPISODE + "}";
		
		private WatchingNowBuilder(ShowService service, String query) {
			super(service, new TypeToken<List<UserProfile>>() {}, URI);
			
			this.field(FIELD_QUERY, query);
		}
		
		/**
		 * Return only users for a specific episode.
		 * 
		 * @param season The season number. Use 0 if you want the specials.
		 * @param episode The episode number.
		 * @return Builder instance.
		 */
		public WatchingNowBuilder episode(int season, int episode) {
			this.field(FIELD_SEASON, season);
			this.field(FIELD_EPISODE, episode);
			
			return this;
		}
	}
	
	/**
	 * Returns detailed episode info for a specific season of a show.
	 * 
	 * @param query Either the slug (i.e. the-walking-dead) or TVDB ID. You can
	 * get a show's slug by browsing the website and looking at the URL when on
	 * a show summary page.
	 * @param season The season number. Use 0 if you want the specials.
	 * @return Builder instance.
	 */
	public SeasonBuilder season(String query, int season) {
		return new SeasonBuilder(this, query, season);
	}
	
	public static final class SeasonBuilder extends TraktApiBuilder<List<TvShowEpisode>> {
		private static final String URI = "/show/season.json/{" + FIELD_API_KEY + "}/{" + FIELD_QUERY + "}/{" + FIELD_SEASON + "}";
		
		private SeasonBuilder(ShowService service, String query, int season) {
			super(service, new TypeToken<List<TvShowEpisode>>() {}, URI);
			
			this.field(FIELD_QUERY, query);
			this.field(FIELD_SEASON, season);
		}
	}
	
	/**
	 * Returns basic season info for a show.
	 * 
	 * @param query Either the slug (i.e. the-walking-dead) or TVDB ID. You can
	 * get a show's slug by browsing the website and looking at the URL when on
	 * a show summary page.
	 * @return Builder instance.
	 */
	public SeasonsBuilder seasons(String query) {
		return new SeasonsBuilder(this, query);
	}
	
	public static final class SeasonsBuilder extends TraktApiBuilder<List<TvShowSeason>> {
		private static final String URI = "/show/seasons.json/{" + FIELD_API_KEY + "}/{" + FIELD_QUERY + "}";
		
		private SeasonsBuilder(ShowService service, String query) {
			super(service, new TypeToken<List<TvShowSeason>>() {}, URI);
			
			this.field(FIELD_QUERY, query);
		}
	}
	
	/**
	 * Returns information for a TV show including ratings, top watchers, and
	 * most watched episodes.
	 * 
	 * @param query Either the slug (i.e. the-walking-dead) or TVDB ID. You can
	 * get a show's slug by browsing the website and looking at the URL when on
	 * a show summary page.
	 * @return Builder instance.
	 */
	public SummaryBuilder summary(String query) {
		return new SummaryBuilder(this, query);
	}
	
	public static final class SummaryBuilder extends TraktApiBuilder<TvShow> {
		private static final String EXTENDED = "extended";
		
		private static final String URI = "/show/summary.json/{" + FIELD_API_KEY + "}/{" + FIELD_EXTENDED + "}";
		
		private SummaryBuilder(ShowService service, String query) {
			super(service, new TypeToken<TvShow>() {}, URI);
			
			this.field(FIELD_QUERY, query);
		}
		
		/**
		 * Returns complete season and episode info. Only send this if you
		 * really need the full dump. Use the show/seasons and show/season
		 * methods if you only need some of the season or episode info.
		 * 
		 * @return Builder instance.
		 */
		public SummaryBuilder extended() {
			this.field(FIELD_EXTENDED, EXTENDED);
			return this;
		}
	}
}
