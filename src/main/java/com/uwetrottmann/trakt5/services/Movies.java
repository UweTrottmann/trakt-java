/*
 * Copyright 2024 Uwe Trottmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.entities.Comment;
import com.uwetrottmann.trakt5.entities.Credits;
import com.uwetrottmann.trakt5.entities.Movie;
import com.uwetrottmann.trakt5.entities.MovieTranslation;
import com.uwetrottmann.trakt5.entities.Ratings;
import com.uwetrottmann.trakt5.entities.Stats;
import com.uwetrottmann.trakt5.entities.TrendingMovie;
import com.uwetrottmann.trakt5.enums.Extended;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface Movies {

    /**
     * Returns the most popular movies. Popularity is calculated using the rating percentage and the number of ratings.
     *
     * @param page Number of page of results to be returned. If {@code null} defaults to 1.
     * @param limit Number of results to return per page. If {@code null} defaults to 10.
     */
    @GET("movies/popular")
    Call<List<Movie>> popular(
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Returns all movies being watched right now. Movies with the most users are returned first.
     *
     * @param page Number of page of results to be returned. If {@code null} defaults to 1.
     * @param limit Number of results to return per page. If {@code null} defaults to 10.
     */
    @GET("movies/trending")
    Call<List<TrendingMovie>> trending(
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Returns a single movie's details.
     *
     * @param movieId trakt ID, trakt slug, or IMDB ID. Example: "tron-legacy-2010".
     */
    @GET("movies/{id}")
    Call<Movie> summary(
            @Path("id") String movieId,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Returns all translations for a movie, including language and translated values for title, tagline and overview.
     *
     * @param movieId trakt ID, trakt slug, or IMDB ID. Example: "tron-legacy-2010".
     */
    @GET("movies/{id}/translations")
    Call<List<MovieTranslation>> translations(
            @Path("id") String movieId
    );

    /**
     * Returns a single translation for a movie. If the translation does not exist, the returned list will be empty.
     *
     * @param movieId trakt ID, trakt slug, or IMDB ID. Example: "tron-legacy-2010".
     * @param language 2-letter language code (ISO 639-1).
     */
    @GET("movies/{id}/translations/{language}")
    Call<List<MovieTranslation>> translation(
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
    @GET("movies/{id}/comments")
    Call<List<Comment>> comments(
            @Path("id") String movieId,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Returns all actors, directors, writers, and producers for a movie.
     *
     * @param movieId trakt ID, trakt slug, or IMDB ID. Example: "tron-legacy-2010".
     */
    @GET("movies/{id}/people")
    Call<Credits> people(
            @Path("id") String movieId
    );

    /**
     * Returns rating (between 0 and 10) and distribution for a movie.
     *
     * @param movieId trakt ID, trakt slug, or IMDB ID. Example: "tron-legacy-2010".
     */
    @GET("movies/{id}/ratings")
    Call<Ratings> ratings(
            @Path("id") String movieId
    );

    /**
     * Returns lots of movie stats.
     */
    @GET("movies/{id}/stats")
    Call<Stats> stats(
            @Path("id") String movieId
    );

}
