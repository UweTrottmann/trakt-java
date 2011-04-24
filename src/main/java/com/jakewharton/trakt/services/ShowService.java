package com.jakewharton.trakt.services;

import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
		private static final String URI = "/show/cancelwatching/" + FIELD_API_KEY;
		
		private CancelWatchingBuilder(ShowService service) {
			super(service, new TypeToken<Response>() {}, URI, HttpMethod.Post);
		}
	}
	
	/**
	 * Add unwatched episodes to your library.
	 * 
	 * @param imdbId Show IMDB ID.
	 * @return Builder instance.
	 */
	public EpisodeLibraryBuilder episodeLibrary(String imdbId) {
		return new EpisodeLibraryBuilder(this).imdbId(imdbId);
	}
	
	/**
	 * Add unwatched episodes to your library.
	 * 
	 * @param tvdbId Show TVDB ID.
	 * @return Builder instance.
	 */
	public EpisodeLibraryBuilder episodeLibrary(int tvdbId) {
		return new EpisodeLibraryBuilder(this).tvdbId(tvdbId);
	}
	
	/**
	 * Add unwatched episodes to your library.
	 * 
	 * @param title Show title.
	 * @param year Show year.
	 * @return Builder instance.
	 */
	public EpisodeLibraryBuilder episodeLibrary(String title, int year) {
		return new EpisodeLibraryBuilder(this).title(title).year(year);
	}
	
	public static final class EpisodeLibraryBuilder extends TraktApiBuilder<Void> {
		private static final String POST_IMDB_ID = "imdb_id";
		private static final String POST_TVDB_ID = "tvdb_id";
		private static final String POST_TITLE = "title";
		private static final String POST_YEAR = "year";
		private static final String POST_EPISODES = "episodes";
		private static final String POST_SEASON = "season";
		private static final String POST_EPISODE = "episode";
		
		private static final String URI = "/show/episode/library/" + FIELD_API_KEY;
		
		private JsonArray episodeList;
		
		private EpisodeLibraryBuilder(ShowService service) {
			super(service, new TypeToken<Void>() {}, URI, HttpMethod.Post);
			
			this.episodeList = new JsonArray();
		}
		
		/**
		 * Show IMDB ID.
		 * 
		 * @param imdbId Value.
		 * @return Builder instance.
		 */
		public EpisodeLibraryBuilder imdbId(String imdbId) {
			this.postParameter(POST_IMDB_ID, imdbId);
			return this;
		}
		
		/**
		 * Show TVDB ID.
		 * 
		 * @param tvdbId Value.
		 * @return Builder instance.
		 */
		public EpisodeLibraryBuilder tvdbId(int tvdbId) {
			this.postParameter(POST_TVDB_ID, tvdbId);
			return this;
		}
		
		/**
		 * Show title.
		 * 
		 * @param title Value.
		 * @return Builder instance.
		 */
		public EpisodeLibraryBuilder title(String title) {
			this.postParameter(POST_TITLE, title);
			return this;
		}
		
		/**
		 * Show year.
		 * 
		 * @param year Value.
		 * @return Builder instance.
		 */
		public EpisodeLibraryBuilder year(int year) {
			this.postParameter(POST_YEAR, year);
			return this;
		}
		
		/**
		 * Add an episode to the list.
		 * 
		 * @param season Episode's season number.
		 * @param episode Episode's number.
		 * @return Builder instance.
		 */
		public EpisodeLibraryBuilder episode(int season, int episode) {
			JsonObject ep = new JsonObject();
			ep.addProperty(POST_SEASON, season);
			ep.addProperty(POST_EPISODE, episode);
			this.episodeList.add(ep);
			return this;
		}

		@Override
		protected void preFireCallback() {
			//Add the assembled movie list to the JSON post body.
			this.postParameter(POST_EPISODES, this.episodeList);
		}
	}
	
	/**
	 * Add episodes watched outside of trakt to your library.
	 * 
	 * @param imdbId Show IMDB ID.
	 * @return Builder instance.
	 */
	public EpisodeSeenBuilder episodeSeen(String imdbId) {
		return new EpisodeSeenBuilder(this).imdbId(imdbId);
	}
	
	/**
	 * Add episodes watched outside of trakt to your library.
	 * 
	 * @param tvdbId Show TVDB ID.
	 * @return Builder instance.
	 */
	public EpisodeSeenBuilder episodeSeen(int tvdbId) {
		return new EpisodeSeenBuilder(this).tvdbId(tvdbId);
	}
	
	/**
	 * Add episodes watched outside of trakt to your library.
	 * 
	 * @param title Show title.
	 * @param year Show year.
	 * @return Builder instance.
	 */
	public EpisodeSeenBuilder episodeSeen(String title, int year) {
		return new EpisodeSeenBuilder(this).title(title).year(year);
	}
	
	public static final class EpisodeSeenBuilder extends TraktApiBuilder<Void> {
		private static final String POST_IMDB_ID = "imdb_id";
		private static final String POST_TVDB_ID = "tvdb_id";
		private static final String POST_TITLE = "title";
		private static final String POST_YEAR = "year";
		private static final String POST_EPISODES = "episodes";
		private static final String POST_SEASON = "season";
		private static final String POST_EPISODE = "episode";
		
		private static final String URI = "/show/episode/seen/" + FIELD_API_KEY;
		
		private JsonArray episodeList;
		
		private EpisodeSeenBuilder(ShowService service) {
			super(service, new TypeToken<Void>() {}, URI, HttpMethod.Post);
			
			this.episodeList = new JsonArray();
		}
		
		/**
		 * Show IMDB ID.
		 * 
		 * @param imdbId Value.
		 * @return Builder instance.
		 */
		public EpisodeSeenBuilder imdbId(String imdbId) {
			this.postParameter(POST_IMDB_ID, imdbId);
			return this;
		}
		
		/**
		 * Show TVDB ID.
		 * 
		 * @param tvdbId Value.
		 * @return Builder instance.
		 */
		public EpisodeSeenBuilder tvdbId(int tvdbId) {
			this.postParameter(POST_TVDB_ID, tvdbId);
			return this;
		}
		
		/**
		 * Show title.
		 * 
		 * @param title Value.
		 * @return Builder instance.
		 */
		public EpisodeSeenBuilder title(String title) {
			this.postParameter(POST_TITLE, title);
			return this;
		}
		
		/**
		 * Show year.
		 * 
		 * @param year Value.
		 * @return Builder instance.
		 */
		public EpisodeSeenBuilder year(int year) {
			this.postParameter(POST_YEAR, year);
			return this;
		}
		
		/**
		 * Add an episode to the list.
		 * 
		 * @param season Episode's season number.
		 * @param episode Episode's number.
		 * @return Builder instance.
		 */
		public EpisodeSeenBuilder episode(int season, int episode) {
			JsonObject ep = new JsonObject();
			ep.addProperty(POST_SEASON, season);
			ep.addProperty(POST_EPISODE, episode);
			this.episodeList.add(ep);
			return this;
		}

		@Override
		protected void preFireCallback() {
			//Add the assembled movie list to the JSON post body.
			this.postParameter(POST_EPISODES, this.episodeList);
		}
	}
	
	/**
	 * Remove episodes from your library collection.
	 * 
	 * @param imdbId Show IMDB ID.
	 * @return Builder instance.
	 */
	public EpisodeUnlibraryBuilder episodeUnlibrary(String imdbId) {
		return new EpisodeUnlibraryBuilder(this).imdbId(imdbId);
	}
	
	/**
	 * Remove episodes from your library collection.
	 * 
	 * @param tvdbId Show TVDB ID.
	 * @return Builder instance.
	 */
	public EpisodeUnlibraryBuilder episodeUnlibrary(int tvdbId) {
		return new EpisodeUnlibraryBuilder(this).tvdbId(tvdbId);
	}
	
	/**
	 * Remove episodes from your library collection.
	 * 
	 * @param title Show title.
	 * @param year Show year.
	 * @return Builder instance.
	 */
	public EpisodeUnlibraryBuilder episodeUnlibrary(String title, int year) {
		return new EpisodeUnlibraryBuilder(this).title(title).year(year);
	}
	
	public static final class EpisodeUnlibraryBuilder extends TraktApiBuilder<Void> {
		private static final String POST_IMDB_ID = "imdb_id";
		private static final String POST_TVDB_ID = "tvdb_id";
		private static final String POST_TITLE = "title";
		private static final String POST_YEAR = "year";
		private static final String POST_EPISODES = "episodes";
		private static final String POST_SEASON = "season";
		private static final String POST_EPISODE = "episode";
		
		private static final String URI = "/show/episode/unlibrary/" + FIELD_API_KEY;
		
		private JsonArray episodeList;
		
		private EpisodeUnlibraryBuilder(ShowService service) {
			super(service, new TypeToken<Void>() {}, URI, HttpMethod.Post);
			
			this.episodeList = new JsonArray();
		}
		
		/**
		 * Show IMDB ID.
		 * 
		 * @param imdbId Value.
		 * @return Builder instance.
		 */
		public EpisodeUnlibraryBuilder imdbId(String imdbId) {
			this.postParameter(POST_IMDB_ID, imdbId);
			return this;
		}
		
		/**
		 * Show TVDB ID.
		 * 
		 * @param tvdbId Value.
		 * @return Builder instance.
		 */
		public EpisodeUnlibraryBuilder tvdbId(int tvdbId) {
			this.postParameter(POST_TVDB_ID, tvdbId);
			return this;
		}
		
		/**
		 * Show title.
		 * 
		 * @param title Value.
		 * @return Builder instance.
		 */
		public EpisodeUnlibraryBuilder title(String title) {
			this.postParameter(POST_TITLE, title);
			return this;
		}
		
		/**
		 * Show year.
		 * 
		 * @param year Value.
		 * @return Builder instance.
		 */
		public EpisodeUnlibraryBuilder year(int year) {
			this.postParameter(POST_YEAR, year);
			return this;
		}
		
		/**
		 * Add an episode to the list.
		 * 
		 * @param season Episode's season number.
		 * @param episode Episode's number.
		 * @return Builder instance.
		 */
		public EpisodeUnlibraryBuilder episode(int season, int episode) {
			JsonObject ep = new JsonObject();
			ep.addProperty(POST_SEASON, season);
			ep.addProperty(POST_EPISODE, episode);
			this.episodeList.add(ep);
			return this;
		}

		@Override
		protected void preFireCallback() {
			//Add the assembled movie list to the JSON post body.
			this.postParameter(POST_EPISODES, this.episodeList);
		}
	}
	
	/**
	 * Remove episodes watched outside of trakt from your library.
	 * 
	 * @param imdbId Show IMDB ID.
	 * @return Builder instance.
	 */
	public EpisodeUnseenBuilder episodeUnseen(String imdbId) {
		return new EpisodeUnseenBuilder(this).imdbId(imdbId);
	}
	
	/**
	 * Remove episodes watched outside of trakt from your library.
	 * 
	 * @param tvdbId Show TVDB ID.
	 * @return Builder instance.
	 */
	public EpisodeUnseenBuilder episodeUnseen(int tvdbId) {
		return new EpisodeUnseenBuilder(this).tvdbId(tvdbId);
	}
	
	/**
	 * Remove episodes watched outside of trakt from your library.
	 * 
	 * @param title Show title.
	 * @param year Show year.
	 * @return Builder instance.
	 */
	public EpisodeUnseenBuilder episodeUnseen(String title, int year) {
		return new EpisodeUnseenBuilder(this).title(title).year(year);
	}
	
	public static final class EpisodeUnseenBuilder extends TraktApiBuilder<Void> {
		private static final String POST_IMDB_ID = "imdb_id";
		private static final String POST_TVDB_ID = "tvdb_id";
		private static final String POST_TITLE = "title";
		private static final String POST_YEAR = "year";
		private static final String POST_EPISODES = "episodes";
		private static final String POST_SEASON = "season";
		private static final String POST_EPISODE = "episode";
		
		private static final String URI = "/show/episode/unseen/" + FIELD_API_KEY;
		
		private JsonArray episodeList;
		
		private EpisodeUnseenBuilder(ShowService service) {
			super(service, new TypeToken<Void>() {}, URI, HttpMethod.Post);
			
			this.episodeList = new JsonArray();
		}
		
		/**
		 * Show IMDB ID.
		 * 
		 * @param imdbId Value.
		 * @return Builder instance.
		 */
		public EpisodeUnseenBuilder imdbId(String imdbId) {
			this.postParameter(POST_IMDB_ID, imdbId);
			return this;
		}
		
		/**
		 * Show TVDB ID.
		 * 
		 * @param tvdbId Value.
		 * @return Builder instance.
		 */
		public EpisodeUnseenBuilder tvdbId(int tvdbId) {
			this.postParameter(POST_TVDB_ID, tvdbId);
			return this;
		}
		
		/**
		 * Show title.
		 * 
		 * @param title Value.
		 * @return Builder instance.
		 */
		public EpisodeUnseenBuilder title(String title) {
			this.postParameter(POST_TITLE, title);
			return this;
		}
		
		/**
		 * Show year.
		 * 
		 * @param year Value.
		 * @return Builder instance.
		 */
		public EpisodeUnseenBuilder year(int year) {
			this.postParameter(POST_YEAR, year);
			return this;
		}
		
		/**
		 * Add an episode to the list.
		 * 
		 * @param season Episode's season number.
		 * @param episode Episode's number.
		 * @return Builder instance.
		 */
		public EpisodeUnseenBuilder episode(int season, int episode) {
			JsonObject ep = new JsonObject();
			ep.addProperty(POST_SEASON, season);
			ep.addProperty(POST_EPISODE, episode);
			this.episodeList.add(ep);
			return this;
		}

		@Override
		protected void preFireCallback() {
			//Add the assembled movie list to the JSON post body.
			this.postParameter(POST_EPISODES, this.episodeList);
		}
	}
	
	/**
	 * Remove one or more episodes for a specific show from your watchlist.
	 * 
	 * @param imdbId Show IMDB ID.
	 * @return Builder instance.
	 */
	public EpisodeUnwatchlistBuilder episodeUnwatchlist(String imdbId) {
		return new EpisodeUnwatchlistBuilder(this).imdbId(imdbId);
	}
	
	/**
	 * Remove one or more episodes for a specific show from your watchlist.
	 * 
	 * @param tvdbId Show TVDB ID.
	 * @return Builder instance.
	 */
	public EpisodeUnwatchlistBuilder episodeUnwatchlist(int tvdbId) {
		return new EpisodeUnwatchlistBuilder(this).tvdbId(tvdbId);
	}
	
	/**
	 * Remove one or more episodes for a specific show from your watchlist.
	 * 
	 * @param title Show title.
	 * @param year Show year.
	 * @return Builder instance.
	 */
	public EpisodeUnwatchlistBuilder episodeUnwatchlist(String title, int year) {
		return new EpisodeUnwatchlistBuilder(this).title(title).year(year);
	}
	
	public static final class EpisodeUnwatchlistBuilder extends TraktApiBuilder<Void> {
		private static final String POST_IMDB_ID = "imdb_id";
		private static final String POST_TVDB_ID = "tvdb_id";
		private static final String POST_TITLE = "title";
		private static final String POST_YEAR = "year";
		private static final String POST_EPISODES = "episodes";
		private static final String POST_SEASON = "season";
		private static final String POST_EPISODE = "episode";
		
		private static final String URI = "/show/episode/unwatchlist/" + FIELD_API_KEY;
		
		private JsonArray episodeList;
		
		private EpisodeUnwatchlistBuilder(ShowService service) {
			super(service, new TypeToken<Void>() {}, URI, HttpMethod.Post);
			
			this.episodeList = new JsonArray();
		}
		
		/**
		 * Show IMDB ID.
		 * 
		 * @param imdbId Value.
		 * @return Builder instance.
		 */
		public EpisodeUnwatchlistBuilder imdbId(String imdbId) {
			this.postParameter(POST_IMDB_ID, imdbId);
			return this;
		}
		
		/**
		 * Show TVDB ID.
		 * 
		 * @param tvdbId Value.
		 * @return Builder instance.
		 */
		public EpisodeUnwatchlistBuilder tvdbId(int tvdbId) {
			this.postParameter(POST_TVDB_ID, tvdbId);
			return this;
		}
		
		/**
		 * Show title.
		 * 
		 * @param title Value.
		 * @return Builder instance.
		 */
		public EpisodeUnwatchlistBuilder title(String title) {
			this.postParameter(POST_TITLE, title);
			return this;
		}
		
		/**
		 * Show year.
		 * 
		 * @param year Value.
		 * @return Builder instance.
		 */
		public EpisodeUnwatchlistBuilder year(int year) {
			this.postParameter(POST_YEAR, year);
			return this;
		}
		
		/**
		 * Add an episode to the list.
		 * 
		 * @param season Episode's season number.
		 * @param episode Episode's number.
		 * @return Builder instance.
		 */
		public EpisodeUnwatchlistBuilder episode(int season, int episode) {
			JsonObject ep = new JsonObject();
			ep.addProperty(POST_SEASON, season);
			ep.addProperty(POST_EPISODE, episode);
			this.episodeList.add(ep);
			return this;
		}

		@Override
		protected void preFireCallback() {
			//Add the assembled movie list to the JSON post body.
			this.postParameter(POST_EPISODES, this.episodeList);
		}
	}
	
	/**
	 * Returns a array of all users watching an episode.
	 * 
	 * @param title Either the slug (i.e. the-walking-dead) or TVDB ID. You can
	 * get a show's slug by browsing the website and looking at the URL when on
	 * a show summary page.
	 * @param season The season number. Use 0 if you want the specials.
	 * @param episode The episode number.
	 * @return Builder instance.
	 */
	public EpisodeWatchingNowBuilder episodeWatchingNow(String title, int season, int episode) {
		return new EpisodeWatchingNowBuilder(this).title(title).season(season).episode(episode);
	}
	
	public static final class EpisodeWatchingNowBuilder extends TraktApiBuilder<List<UserProfile>> {
		private static final String URI = "/show/watchingnow.json/" + FIELD_API_KEY + "/" + FIELD_TITLE + "/" + FIELD_SEASON + "/" + FIELD_EPISODE;
		
		private EpisodeWatchingNowBuilder(ShowService service) {
			super(service, new TypeToken<List<UserProfile>>() {}, URI);
		}
		
		/**
		 * Either the slug (i.e. the-walking-dead) or TVDB ID. You can get a
		 * show's slug by browsing the website and looking at the URL when on a
		 * show summary page.
		 * 
		 * @param title Value.
		 * @return Builder instance.
		 */
		public EpisodeWatchingNowBuilder title(String title) {
			this.field(FIELD_TITLE, title);
			return this;
		}
		
		/**
		 * The season number. Use 0 if you want the specials.
		 * 
		 * @param season Value.
		 * @return Builder instance.
		 */
		public EpisodeWatchingNowBuilder season(int season) {
			this.field(FIELD_SEASON, season);
			return this;
		}
		
		/**
		 * The episode number.
		 * 
		 * @param episode Value
		 * @return Builder instance.
		 */
		public EpisodeWatchingNowBuilder episode(int episode) {
			this.field(FIELD_EPISODE, episode);
			return this;
		}
	}
	
	/**
	 * Add one or more episodes for a specific show to your watchlist.
	 * 
	 * @param imdbId Show IMDB ID.
	 * @return Builder instance.
	 */
	public EpisodeWatchlistBuilder episodeWatchlist(String imdbId) {
		return new EpisodeWatchlistBuilder(this).imdbId(imdbId);
	}
	
	/**
	 * Add one or more episodes for a specific show to your watchlist.
	 * 
	 * @param tvdbId Show TVDB ID.
	 * @return Builder instance.
	 */
	public EpisodeWatchlistBuilder episodeWatchlist(int tvdbId) {
		return new EpisodeWatchlistBuilder(this).tvdbId(tvdbId);
	}
	
	/**
	 * Add one or more episodes for a specific show to your watchlist.
	 * 
	 * @param title Show title.
	 * @param year Show year.
	 * @return Builder instance.
	 */
	public EpisodeWatchlistBuilder episodeWatchlist(String title, int year) {
		return new EpisodeWatchlistBuilder(this).title(title).year(year);
	}
	
	public static final class EpisodeWatchlistBuilder extends TraktApiBuilder<Void> {
		private static final String POST_IMDB_ID = "imdb_id";
		private static final String POST_TVDB_ID = "tvdb_id";
		private static final String POST_TITLE = "title";
		private static final String POST_YEAR = "year";
		private static final String POST_EPISODES = "episodes";
		private static final String POST_SEASON = "season";
		private static final String POST_EPISODE = "episode";
		
		private static final String URI = "/show/episode/watchlist/" + FIELD_API_KEY;
		
		private JsonArray episodeList;
		
		private EpisodeWatchlistBuilder(ShowService service) {
			super(service, new TypeToken<Void>() {}, URI, HttpMethod.Post);
			
			this.episodeList = new JsonArray();
		}
		
		/**
		 * Show IMDB ID.
		 * 
		 * @param imdbId Value.
		 * @return Builder instance.
		 */
		public EpisodeWatchlistBuilder imdbId(String imdbId) {
			this.postParameter(POST_IMDB_ID, imdbId);
			return this;
		}
		
		/**
		 * Show TVDB ID.
		 * 
		 * @param tvdbId Value.
		 * @return Builder instance.
		 */
		public EpisodeWatchlistBuilder tvdbId(int tvdbId) {
			this.postParameter(POST_TVDB_ID, tvdbId);
			return this;
		}
		
		/**
		 * Show title.
		 * 
		 * @param title Value.
		 * @return Builder instance.
		 */
		public EpisodeWatchlistBuilder title(String title) {
			this.postParameter(POST_TITLE, title);
			return this;
		}
		
		/**
		 * Show year.
		 * 
		 * @param year Value.
		 * @return Builder instance.
		 */
		public EpisodeWatchlistBuilder year(int year) {
			this.postParameter(POST_YEAR, year);
			return this;
		}
		
		/**
		 * Add an episode to the list.
		 * 
		 * @param season Episode's season number.
		 * @param episode Episode's number.
		 * @return Builder instance.
		 */
		public EpisodeWatchlistBuilder episode(int season, int episode) {
			JsonObject ep = new JsonObject();
			ep.addProperty(POST_SEASON, season);
			ep.addProperty(POST_EPISODE, episode);
			this.episodeList.add(ep);
			return this;
		}

		@Override
		protected void preFireCallback() {
			//Add the assembled movie list to the JSON post body.
			this.postParameter(POST_EPISODES, this.episodeList);
		}
	}
	
	/**
	 * <p>Notify Trakt that a user has finished watching a show. This commits
	 * the show to the users profile. You should use show/watching prior to
	 * calling this method.</p>
	 * 
	 * <p><em>Warning</em>: This method requires a developer API key.</p>
	 * 
	 * @param tmdbId TVDB ID for the show. 
	 * @return Builder instance.
	 */
	public ScrobbleBuilder scrobble(int tvdbId) {
		return new ScrobbleBuilder(this).tvdbId(tvdbId);
	}
	
	/**
	 * <p>Notify Trakt that a user has finished watching a show. This commits
	 * the show to the users profile. You should use show/watching prior to
	 * calling this method.</p>
	 * 
	 * <p><em>Warning</em>: This method requires a developer API key.</p>
	 * 
	 * @param title Show title.
	 * @param year Show year.
	 * @return Builder instance.
	 */
	public ScrobbleBuilder scrobble(String title, int year) {
		return new ScrobbleBuilder(this).title(title).year(year);
	}
	
	public static final class ScrobbleBuilder extends TraktApiBuilder<Response> {
		private static final String POST_TVDB_ID = "tvdb_id";
		private static final String POST_TITLE = "title";
		private static final String POST_YEAR = "year";
		private static final String POST_SEASON = "season";
		private static final String POST_EPISODE = "episode";
		private static final String POST_DURATION = "duration";
		private static final String POST_PROGRESS = "progress";
		
		private static final String URI = "/show/scrobble/" + FIELD_API_KEY;
		
		private ScrobbleBuilder(ShowService service) {
			super(service, new TypeToken<Response>() {}, URI, HttpMethod.Post);
			this.includeDebugStrings();
		}
		
		/**
		 * TVDB ID for the show.
		 * 
		 * @param tmdbId Value.
		 * @return Builder instance.
		 */
		public ScrobbleBuilder tvdbId(int tmdbId) {
			this.postParameter(POST_TVDB_ID, tmdbId);
			return this;
		}
		
		/**
		 * Movie title.
		 * 
		 * @param title Value.
		 * @return Builder instance.
		 */
		public ScrobbleBuilder title(String title) {
			this.postParameter(POST_TITLE, title);
			return this;
		}
		
		/**
		 * Movie year.
		 * 
		 * @param year Value.
		 * @return Builder instance.
		 */
		public ScrobbleBuilder year(int year) {
			this.postParameter(POST_YEAR, year);
			return this;
		}
		
		/**
		 * Show season. Send 0 if watching a special.
		 * 
		 * @param season Value.
		 * @return Builder instance.
		 */
		public ScrobbleBuilder season(int season) {
			this.postParameter(POST_SEASON, season);
			return this;
		}
		
		/**
		 * Show episode. 
		 * 
		 * @param episode Value.
		 * @return Builder instance.
		 */
		public ScrobbleBuilder episode(int episode) {
			this.postParameter(POST_EPISODE, episode);
			return this;
		}
		
		/**
		 * Duration (in minutes).
		 * 
		 * @param duration Value.
		 * @return Builder instance.
		 */
		public ScrobbleBuilder duration(int duration) {
			this.postParameter(POST_DURATION, duration);
			return this;
		}
		
		/**
		 * Percent progress (0-100). It is recommended to call the watching API
		 * every 15 minutes, then call the scrobble API near the end of the
		 * movie to lock it in.
		 * 
		 * @param progress Value.
		 * @return Builder instance.
		 */
		public ScrobbleBuilder progress(int progress) {
			this.postParameter(POST_PROGRESS, progress);
			return this;
		}

		@Override
		protected void performValidation() {
			assert this.hasPostParameter(POST_TVDB_ID)
			|| (this.hasPostParameter(POST_TITLE) && this.hasPostParameter(POST_YEAR))
			: "Either IMDB ID, TMDB ID, or both title and year is required.";
			assert this.hasPostParameter(POST_SEASON) : "Season is required.";
			assert this.hasPostParameter(POST_EPISODE) : "Episode is required.";
			assert this.hasPostParameter(POST_DURATION) : "Duration is required.";
			assert this.hasPostParameter(POST_PROGRESS) : "Progress is required.";
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
		private static final String URI = "/show/season.json/" + FIELD_API_KEY + "/" + FIELD_QUERY + "/" + FIELD_SEASON;
		
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
		private static final String URI = "/show/seasons.json/" + FIELD_API_KEY + "/" + FIELD_QUERY;
		
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
		
		private static final String URI = "/show/summary.json/" + FIELD_API_KEY + "/" + FIELD_EXTENDED;
		
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

	/**
	 * Returns a array of all users watching a show.
	 * 
	 * @param query Either the slug (i.e. the-walking-dead) or TVDB ID. You can
	 * get a show's slug by browsing the website and looking at the URL when on
	 * a show summary page.
	 * @return Builder instance.
	 */
	public WatchingNowBuilder watchingNow(String query) {
		return new WatchingNowBuilder(this).query(query);
	}
	
	public static final class WatchingNowBuilder extends TraktApiBuilder<List<UserProfile>> {
		private static final String URI = "/show/watchingnow.json/" + FIELD_API_KEY + "/" + FIELD_QUERY;
		
		private WatchingNowBuilder(ShowService service) {
			super(service, new TypeToken<List<UserProfile>>() {}, URI);
		}
		
		/**
		 * Either the slug (i.e. the-walking-dead) or TVDB ID. You can get a
		 * show's slug by browsing the website and looking at the URL when on
		 * a show summary page.
		 * 
		 * @param query Value.
		 * @return Builder instance.
		 */
		public WatchingNowBuilder query(String query) {
			this.field(FIELD_QUERY, query);
			return this;
		}
	}
}
