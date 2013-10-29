package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvShow;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.POST;

public interface RecommendationsService {

    /**
     * Get a list of movie recommendations created from your watching history and your friends.
     * Results returned with the top recommendation first.
     */
    @POST("/recommendations/movies/{apikey}")
    List<Movie> movies();

    /**
     * Get a list of movie recommendations created from your watching history and your friends.
     * Results returned with the top recommendation first.
     */
    @POST("/recommendations/movies/{apikey}")
    List<Movie> movies(
            @Body RecommendationsQuery query
    );

    /**
     * Get a list of show recommendations created from your watching history and your friends.
     * Results returned with the top recommendation first.
     */
    @POST("/recommendations/shows/{apikey}")
    List<TvShow> shows();

    /**
     * Get a list of show recommendations created from your watching history and your friends.
     * Results returned with the top recommendation first.
     */
    @POST("/recommendations/shows/{apikey}")
    List<TvShow> shows(
            @Body RecommendationsQuery query
    );

    public static class RecommendationsQuery {

        public String genre;

        public Integer start_year;

        public Integer end_year;

        public Boolean hide_collected;

        public Boolean hide_watchlisted;

        /**
         * <em>Optional.</em> Genre slug to filter by. See genres/movies or genres/shows for a list
         * of valid genres.
         */
        public RecommendationsQuery genre(String genre) {
            this.genre = genre;
            return this;
        }

        /**
         * <em>Optional.</em> 4 digit year to filter movies released in this year or later.
         */
        public RecommendationsQuery startYear(int startYear) {
            this.start_year = startYear;
            return this;
        }

        /**
         * <em>Optional.</em> 4 digit year to filter movies released in this year or earlier.
         */
        public RecommendationsQuery endYear(int endYear) {
            this.end_year = endYear;
            return this;
        }

        /**
         * <em>Optional.</em> Set to true to hide any movies the user has collected.
         */
        public RecommendationsQuery hideCollected(boolean isHidingCollected) {
            this.hide_collected = isHidingCollected;
            return this;
        }

        /**
         * <em>Optional.</em> Set to true to hide any movies on the user's watchlist.
         */
        public RecommendationsQuery hideWatchlisted(boolean isHidingWatchlisted) {
            this.hide_watchlisted = isHidingWatchlisted;
            return this;
        }
    }

}
