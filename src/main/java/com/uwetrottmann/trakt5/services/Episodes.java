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

import com.uwetrottmann.trakt5.TraktV2;
import com.uwetrottmann.trakt5.entities.Comment;
import com.uwetrottmann.trakt5.entities.Episode;
import com.uwetrottmann.trakt5.entities.Ratings;
import com.uwetrottmann.trakt5.entities.Stats;
import com.uwetrottmann.trakt5.enums.CommentsSortBy;
import com.uwetrottmann.trakt5.enums.Extended;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

import javax.annotation.Nonnull;
import java.util.List;

import static com.uwetrottmann.trakt5.TraktV2.HEADER_CACHE_CONTROL;
import static com.uwetrottmann.trakt5.TraktV2.QUERY_PARAM_EXTENDED;
import static com.uwetrottmann.trakt5.TraktV2.QUERY_PARAM_LANGUAGE;
import static com.uwetrottmann.trakt5.TraktV2.QUERY_PARAM_LIMIT;
import static com.uwetrottmann.trakt5.TraktV2.QUERY_PARAM_PAGE;

public interface Episodes {

    String PATH_SORT_BY = "sort_by";

    /**
     * Returns a single episode's details.
     *
     * @param showId  Trakt ID, Trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season  Season number.
     * @param episode Episode number.
     */
    @GET("shows/{id}/seasons/{season}/episodes/{episode}")
    Call<Episode> summary(
            @Path("id") String showId,
            @Path("season") int season,
            @Path("episode") int episode,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * Returns all top level comments for an episode. Most recent comments returned first.
     *
     * @param showId  Trakt ID, Trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season  Season number.
     * @param episode Episode number.
     * @deprecated Use {@link #comments(String, int, int, CommentsSortBy, Integer, Integer, String, Extended, String)}
     * instead.
     */
    @Deprecated
    @GET("shows/{id}/seasons/{season}/episodes/{episode}/comments")
    Call<List<Comment>> comments(
            @Path("id") String showId,
            @Path("season") int season,
            @Path("episode") int episode,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * Variant that allows changing the Cache-Control header.
     *
     * @deprecated Use {@link #comments(String, int, int, CommentsSortBy, Integer, Integer, String, Extended, String)}
     * instead.
     */
    @Deprecated
    @GET("shows/{id}/seasons/{season}/episodes/{episode}/comments")
    Call<List<Comment>> comments(
            @Path("id") String showId,
            @Path("season") int season,
            @Path("episode") int episode,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended,
            @Header(HEADER_CACHE_CONTROL) String cacheControl
    );

    /**
     * Returns all top-level comments for an episode.
     * <p>
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * If you send OAuth, comments from blocked users will be automatically filtered out.
     *
     * @param showId       The id/slug of the show.
     * @param season       Season number.
     * @param episode      Episode number.
     * @param sortBy       How to sort, for example {@link CommentsSortBy#NEWEST}. Use {@link CommentsSortBy#of(String)}
     *                     for values without a constant.
     * @param page         Number of page of results to be returned. If {@code null} defaults to 1.
     * @param limit        Number of results to return per page. If {@code null} defaults to 10.
     * @param language     Filter comments to a 2-letter language code (ISO 639-1).
     * @param extended     Extended information to include in the response.
     * @param cacheControl Optionally, allows changing the Cache-Control header.
     */
    @GET("shows/{id}/seasons/{season}/episodes/{episode}/comments/{" + PATH_SORT_BY + "}")
    Call<List<Comment>> comments(
            @Path("id") String showId,
            @Path("season") int season,
            @Path("episode") int episode,
            @Nonnull @Path(PATH_SORT_BY) CommentsSortBy sortBy,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(QUERY_PARAM_LANGUAGE) String language,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended,
            @Header(HEADER_CACHE_CONTROL) String cacheControl
    );

    /**
     * Returns rating (between 0 and 10) and distribution for an episode.
     *
     * @param showId  Trakt ID, Trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season  Season number.
     * @param episode Episode number.
     */
    @GET("shows/{id}/seasons/{season}/episodes/{episode}/ratings")
    Call<Ratings> ratings(
            @Path("id") String showId,
            @Path("season") int season,
            @Path("episode") int episode
    );

    /**
     * Returns lots of episode stats.
     */
    @GET("shows/{id}/seasons/{season}/episodes/{episode}/stats")
    Call<Stats> stats(
            @Path("id") String showId,
            @Path("season") int season,
            @Path("episode") int episode
    );

}
