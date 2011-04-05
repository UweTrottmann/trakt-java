package com.jakewharton.trakt.services;

import java.io.Serializable;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.trakt.TraktApiBuilder;
import com.jakewharton.trakt.TraktApiService;
import com.jakewharton.trakt.entities.RatingResponse;
import com.jakewharton.trakt.enumerations.Rating;

public class RateService extends TraktApiService {
	/**
	 * Rate an episode on Trakt. Depending on the user settings, this will also
	 * send out social updates to Facebook, Twitter, and Tumblr.
	 * 
	 * @param tvdbId TVDB ID for the show.
	 * @param season Show season. Send 0 if watching a special.
	 * @param episode Show episode.
	 * @param rating User rating.
	 * @return Builder instance.
	 */
	public EpisodeBuilder episode(int tvdbId, int season, int episode, Rating rating) {
		return new EpisodeBuilder(this,
			new EpisodeBuilder.Parameters()
					.setTvdbId(tvdbId)
					.setSeason(season)
					.setEpisode(episode)
					.setRating(rating)
		);
	}
	
	/**
	 * Rate an episode on Trakt. Depending on the user settings, this will also
	 * send out social updates to Facebook, Twitter, and Tumblr.
	 * 
	 * @param title Show title.
	 * @param year Show year.
	 * @param season Show season. Send 0 if watching a special.
	 * @param episode Show episode.
	 * @param rating User rating.
	 * @return Builder instance.
	 */
	public EpisodeBuilder episode(String title, int year, int season, int episode, Rating rating) {
		return new EpisodeBuilder(this,
				new EpisodeBuilder.Parameters()
						.setTitle(title)
						.setYear(year)
						.setSeason(season)
						.setEpisode(episode)
						.setRating(rating)
			);
	}
	
	public static final class EpisodeBuilder extends TraktApiBuilder<RatingResponse> {
		private static final String URI = "/rate/episode/{" + FIELD_API_KEY + "}";

		static final class Parameters implements Serializable {
			private static final long serialVersionUID = -2457836489210716934L;
			
			@SuppressWarnings("unused") private Integer tvdbId;
			@SuppressWarnings("unused") private String title;
			@SuppressWarnings("unused") private Integer year;
			@SuppressWarnings("unused") private Integer season;
			@SuppressWarnings("unused") private Integer episode;
			@SuppressWarnings("unused") private Rating rating;
			
			public Parameters setTvdbId(Integer tvdbId) {
				this.tvdbId = tvdbId;
				return this;
			}
			public Parameters setTitle(String title) {
				this.title = title;
				return this;
			}
			public Parameters setYear(Integer year) {
				this.year = year;
				return this;
			}
			public Parameters setSeason(Integer season) {
				this.season = season;
				return this;
			}
			public Parameters setEpisode(Integer episode) {
				this.episode = episode;
				return this;
			}
			public Parameters setRating(Rating rating) {
				this.rating = rating;
				return this;
			}
		}
		
		private EpisodeBuilder(RateService service, Parameters parameters) {
			super(service, new TypeToken<RatingResponse>() {}, URI, HttpMethod.Post);
			this.postBody(parameters);
		}
	}
	
	/**
	 * Rate a movie on Trakt. Depending on the user settings, this will also
	 * send out social updates to Facebook, Twitter, and Tumblr.
	 * 
	 * @param imdbId IMDB ID for the movie.
	 * @param rating User rating.
	 * @return Builder instance.
	 */
	public MovieBuilder movie(String imdbId, Rating rating) {
		return new MovieBuilder(this,
				new MovieBuilder.Parameters()
						.setImdbId(imdbId)
						.setRating(rating)
		);
	}
	
	/**
	 * Rate a movie on Trakt. Depending on the user settings, this will also
	 * send out social updates to Facebook, Twitter, and Tumblr.
	 * 
	 * @param title Movie title.
	 * @param year Movie year.
	 * @param rating User rating.
	 * @return Builder instance.
	 */
	public MovieBuilder movie(String title, int year, Rating rating) {
		return new MovieBuilder(this,
				new MovieBuilder.Parameters()
						.setTitle(title)
						.setYear(year)
						.setRating(rating)
		);
	}
	
	private static final class MovieBuilder extends TraktApiBuilder<RatingResponse> {
		private static final String URI = "/rate/movie/{" + FIELD_API_KEY + "}";
		
		static final class Parameters implements Serializable {
			private static final long serialVersionUID = 5939126164691001368L;
			
			@SuppressWarnings("unused") private String imdbId;
			@SuppressWarnings("unused") private String title;
			@SuppressWarnings("unused") private Integer year;
			@SuppressWarnings("unused") private Rating rating;
			
			public Parameters setImdbId(String imdbId) {
				this.imdbId = imdbId;
				return this;
			}
			public Parameters setTitle(String title) {
				this.title = title;
				return this;
			}
			public Parameters setYear(Integer year) {
				this.year = year;
				return this;
			}
			public Parameters setRating(Rating rating) {
				this.rating = rating;
				return this;
			}
		}
		
		private MovieBuilder(RateService service, Parameters parameters) {
			super(service, new TypeToken<RatingResponse>() {}, URI, HttpMethod.Post);
			this.postBody(parameters);
		}
	}
	
	/**
	 * Rate a show on Trakt. Depending on the user settings, this will also
	 * send out social updates to Facebook, Twitter, and Tumblr.
	 * 
	 * @param tvdbId TVDB ID for the show.
	 * @param rating User rating.
	 * @return Builder instance.
	 */
	public ShowBuilder show(int tvdbId, Rating rating) {
		return new ShowBuilder(this,
				new ShowBuilder.Parameters()
						.setTvdbId(tvdbId)
						.setRating(rating)
		);
	}
	
	/**
	 * Rate a show on Trakt. Depending on the user settings, this will also
	 * send out social updates to Facebook, Twitter, and Tumblr.
	 * 
	 * @param title Show title.
	 * @param year Show year.
	 * @param rating User rating.
	 * @return Builder instance.
	 */
	public ShowBuilder show(String title, int year, Rating rating) {
		return new ShowBuilder(this,
				new ShowBuilder.Parameters()
						.setTitle(title)
						.setYear(year)
						.setRating(rating)
		);
	}
	
	private static final class ShowBuilder extends TraktApiBuilder<RatingResponse> {
		private static final String URI = "/rate/show/{" + FIELD_API_KEY + "}";
		
		static final class Parameters implements Serializable {
			private static final long serialVersionUID = 5841662891981241955L;
			
			@SuppressWarnings("unused") private Integer tvdbId;
			@SuppressWarnings("unused") private String title;
			@SuppressWarnings("unused") private Integer year;
			@SuppressWarnings("unused") private Rating rating;
			
			public Parameters setTvdbId(Integer tvdbId) {
				this.tvdbId = tvdbId;
				return this;
			}
			public Parameters setTitle(String title) {
				this.title = title;
				return this;
			}
			public Parameters setYear(Integer year) {
				this.year = year;
				return this;
			}
			public Parameters setRating(Rating rating) {
				this.rating = rating;
				return this;
			}
		}
		
		private ShowBuilder(RateService service, Parameters parameters) {
			super(service, new TypeToken<RatingResponse>() {}, URI, HttpMethod.Post);
			this.postBody(parameters);
		}
	}
}
