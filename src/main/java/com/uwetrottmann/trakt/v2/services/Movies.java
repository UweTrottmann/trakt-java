package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Comment;
import com.uwetrottmann.trakt.v2.entities.Movie;
import com.uwetrottmann.trakt.v2.entities.MovieTranslation;
import com.uwetrottmann.trakt.v2.entities.TrendingMovie;
import com.uwetrottmann.trakt.v2.enums.Extended;
import retrofit.http.EncodedQuery;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

public interface Movies {

    /**
     * Returns the most popular movies. Popularity is calculated using the rating percentage and the number of ratings.
     *
     * @param page Number of page of results to be returned. If {@code null} defaults to 1.
     * @param limit Number of results to return per page. If {@code null} defaults to 10.
     */
    @GET("/movies/popular")
    List<Movie> popular(
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );

    /**
     * Returns all movies being watched right now. Movies with the most users are returned first.
     *
     * @param page Number of page of results to be returned. If {@code null} defaults to 1.
     * @param limit Number of results to return per page. If {@code null} defaults to 10.
     */
    @GET("/movies/trending")
    List<TrendingMovie> trending(
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );

    /**
     * Returns a single movie's details.
     *
     * @param movieId trakt ID, trakt slug, or IMDB ID. Example: "tron-legacy-2010".
     */
    @GET("/movies/{id}")
    Movie summary(
            @Path("id") String movieId,
            @EncodedQuery("extended") Extended extended
    );

    /**
     * Returns all translations for a movie, including language and translated values for title, tagline and overview.
     *
     * @param movieId trakt ID, trakt slug, or IMDB ID. Example: "tron-legacy-2010".
     */
    @GET("/movies/{id}/translations")
    List<MovieTranslation> translations(
            @Path("id") String movieId
    );

    /**
     * Returns a single translation for a movie. If the translation does not exist, the returned list will be empty.
     *
     * @param movieId trakt ID, trakt slug, or IMDB ID. Example: "tron-legacy-2010".
     * @param language 2-letter language code (ISO 639-1).
     */
    @GET("/movies/{id}/translations/{language}")
    List<MovieTranslation> translation(
            @Path("id") String movieId,
            @Path("language") String language
    );

    /**
     * Returns all top level comments for a movie. Most recent comments returned first.
     *
     * @param movieId trakt ID, trakt slug, or IMDB ID. Example: "tron-legacy-2010".
     * @param page Number of page of results to be returned. If {@code null} defaults to 1.
     * @param limit Number of results to return per page. If {@code null} defaults to 10.
     */
    @GET("/movies/{id}/comments")
    List<Comment> comments(
            @Path("id") String movieId,
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );

}
