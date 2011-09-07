package com.jakewharton.trakt.services;

import java.util.List;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.trakt.TraktApiBuilder;
import com.jakewharton.trakt.TraktApiService;
import com.jakewharton.trakt.entities.Genre;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvShow;

public class RecommendationsService extends TraktApiService {
	/**
	 * Get a list of movie recommendations created from your watching history
	 * and your friends. Results returned with the top recommendation first.
	 *
	 * @return Builder instance.
	 */
	public MoviesBuilder movies() {
		return new MoviesBuilder(this);
	}

	/**
	 * Get a list of show recommendations created from your watching history
	 * and your friends. Results returned with the top recommendation first.
	 *
	 * @return Builder instance.
	 */
	public ShowsBuilder shows() {
		return new ShowsBuilder(this);
	}


	public static final class MoviesBuilder extends TraktApiBuilder<List<Movie>> {
		private static final String POST_GENRE = "genre";
		private static final String POST_YEAR_START = "start_year";
		private static final String POST_YEAR_END = "end_year";

		private static final String URI = "/recommendations/movies/" + FIELD_API_KEY;

		private MoviesBuilder(RecommendationsService service) {
			super(service, new TypeToken<List<Movie>>() {}, URI, HttpMethod.Post);
		}

		/**
		 * 4 digit year to filter movies released in this year or later.
		 *
		 * @param year Value.
		 * @return Builder instance.
		 */
		public MoviesBuilder startYear(int year) {
			this.postParameter(POST_YEAR_START, year);
			return this;
		}

		/**
		 * 4 digit year to filter movies released in this year or earlier.
		 *
		 * @param year Value.
		 * @return Builder instance.
		 */
		public MoviesBuilder endYear(int year) {
			this.postParameter(POST_YEAR_END, year);
			return this;
		}

		/**
		 * Genre slug to filter by. See {@link GenreService#movies()} for a
		 * list of valid genres.
		 *
		 * @param genre
		 * @return
		 */
		public MoviesBuilder genre(Genre genre) {
			this.postParameter(POST_GENRE, genre.getSlug());
			return this;
		}
	}
	public static final class ShowsBuilder extends TraktApiBuilder<List<TvShow>> {
		private static final String POST_GENRE = "genre";
		private static final String POST_YEAR_START = "start_year";
		private static final String POST_YEAR_END = "end_year";

		private static final String URI = "/recommendations/shows/" + FIELD_API_KEY;

		private ShowsBuilder(RecommendationsService service) {
			super(service, new TypeToken<List<TvShow>>() {}, URI, HttpMethod.Post);
		}

		/**
		 * 4 digit year to filter shows premiering in this year or later.
		 *
		 * @param year Value.
		 * @return Builder instance.
		 */
		public ShowsBuilder startYear(int year) {
			this.postParameter(POST_YEAR_START, year);
			return this;
		}

		/**
		 * 4 digit year to filter shows premiering in this year or earlier.
		 *
		 * @param year Value.
		 * @return Builder instance.
		 */
		public ShowsBuilder endYear(int year) {
			this.postParameter(POST_YEAR_END, year);
			return this;
		}

		/**
		 * Genre slug to filter by. See {@link GenreService#shows()} for a
		 * list of valid genres.
		 *
		 * @param genre
		 * @return
		 */
		public ShowsBuilder genre(Genre genre) {
			this.postParameter(POST_GENRE, genre.getSlug());
			return this;
		}
	}
}
