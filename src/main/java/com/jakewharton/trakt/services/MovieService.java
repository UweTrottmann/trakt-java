package com.jakewharton.trakt.services;

import java.util.List;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.trakt.TraktApiBuilder;
import com.jakewharton.trakt.TraktApiService;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.entities.UserProfile;

public class MovieService extends TraktApiService {
	/**
	 * <p>Notify Trakt that a user has stopped watching a movie.</p>
	 * 
	 * <p><em>Warning</em>: This method requires a developer API key.</p>
	 * 
	 * @return Builder instance.
	 */
	public CancelWatchingBuilder cancelWatching() {
		return new CancelWatchingBuilder(this);
	}
	
	public static final class CancelWatchingBuilder extends TraktApiBuilder<Response> {
		private static final String URI = "/movie/cancelwatching/" + FIELD_API_KEY;
		
		private CancelWatchingBuilder(MovieService service) {
			super(service, new TypeToken<Response>() {}, URI, HttpMethod.Post);
		}
	}
	
	/**
	 * Returns information for a movie including ratings and top watchers.
	 * 
	 * @param query Either the slug (i.e. the-social-network-2010), IMDB ID, or
	 * TMDB ID. You can get a movie's slug by browsing the website and looking
	 * at the URL when on a movie summary page.
	 * @return Builder instance.
	 */
	public SummaryBuilder summary(String query) {
		return new SummaryBuilder(this, query);
	}
	
	public static final class SummaryBuilder extends TraktApiBuilder<Movie> {
		private static final String URI = "/movie/summary.json/" + FIELD_API_KEY + "/" + FIELD_QUERY;
		
		private SummaryBuilder(MovieService service, String query) {
			super(service, new TypeToken<Movie>() {}, URI);
			
			this.field(FIELD_QUERY, query);
		}
	}
	
	/**
	 * Returns a array of all users watching a movie.
	 * 
	 * @param query Either the slug (i.e. the-social-network-2010), IMDB ID, or
	 * TMDB ID. You can get a movie's slug by browsing the website and looking
	 * at the URL when on a movie summary page.
	 * @return Builder instance.
	 */
	public WatchingNowBuilder watchingNow(String query) {
		return new WatchingNowBuilder(this, query);
	}
	
	public static final class WatchingNowBuilder extends TraktApiBuilder<List<UserProfile>> {
		private static final String URI = "/movie/watchingnow.json/" + FIELD_API_KEY + "/" + FIELD_QUERY;
		
		private WatchingNowBuilder(MovieService service, String query) {
			super(service, new TypeToken<List<UserProfile>>() {}, URI);
			
			this.field(FIELD_QUERY, query);
		}
	}
}
