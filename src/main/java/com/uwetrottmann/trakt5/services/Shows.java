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

import com.uwetrottmann.trakt5.entities.BaseShow;
import com.uwetrottmann.trakt5.entities.Comment;
import com.uwetrottmann.trakt5.entities.Credits;
import com.uwetrottmann.trakt5.entities.Ratings;
import com.uwetrottmann.trakt5.entities.Show;
import com.uwetrottmann.trakt5.entities.Stats;
import com.uwetrottmann.trakt5.entities.Translation;
import com.uwetrottmann.trakt5.entities.TrendingShow;
import com.uwetrottmann.trakt5.enums.Extended;
import com.uwetrottmann.trakt5.enums.ProgressLastActivity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface Shows {

    /**
     * Returns the most popular shows. Popularity is calculated using the rating percentage and the number of ratings.
     *
     * @param page Number of page of results to be returned. If {@code null} defaults to 1.
     * @param limit Number of results to return per page. If {@code null} defaults to 10.
     */
    @GET("shows/popular")
    Call<List<Show>> popular(
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Returns all shows being watched right now. Shows with the most users are returned first.
     *
     * @param page Number of page of results to be returned. If {@code null} defaults to 1.
     * @param limit Number of results to return per page. If {@code null} defaults to 10.
     */
    @GET("shows/trending")
    Call<List<TrendingShow>> trending(
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Returns a single shows's details.
     *
     * @param showId Trakt ID, Trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("shows/{id}")
    Call<Show> summary(
            @Path("id") String showId,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Returns all translations for a show, including language and translated values for title and overview.
     *
     * @param showId Trakt ID, Trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("shows/{id}/translations")
    Call<List<Translation>> translations(
            @Path("id") String showId
    );

    /**
     * Returns a single translation for a show. If the translation does not exist, the returned list will be empty.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param language 2-letter language code (ISO 639-1).
     */
    @GET("shows/{id}/translations/{language}")
    Call<List<Translation>> translation(
            @Path("id") String showId,
            @Path("language") String language
    );

    /**
     * Returns all top level comments for a show. Most recent comments returned first.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param page Number of page of results to be returned. If {@code null} defaults to 1.
     * @param limit Number of results to return per page. If {@code null} defaults to 10.
     */
    @GET("shows/{id}/comments")
    Call<List<Comment>> comments(
            @Path("id") String showId,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Variant that allows changing the Cache-Control header.
     */
    @GET("shows/{id}/comments")
    Call<List<Comment>> comments(
            @Path("id") String showId,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended,
            @Header("Cache-Control") String cacheControl
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p>Returns collection progress for show including details on all seasons and episodes. The {@code next_episode}
     * will be the next episode the user should collect, if there are no upcoming episodes it will be set to {@code
     * null}.
     *
     * <p>By default, any hidden seasons will be removed from the response and stats. To include these and adjust the
     * completion stats, set the {@code hidden} flag to {@code true}.
     *
     * <p>By default, specials will be excluded from the response. Set the {@code specials} flag to {@code true} to
     * include season 0 and adjust the stats accordingly. If you'd like to include specials, but not adjust the stats,
     * set {@code count_specials} to {@code false}.
     *
     * <p>By default, the {@code last_episode} and {@code next_episode} are calculated using the last {@code aired}
     * episode the user has collected, even if they've collected older episodes more recently. To use their last
     * collected episode for these calculations, set the {@code last_activity} flag to {@code collected}.
     *
     * <b>Note:</b>
     *
     * <p>Only aired episodes are used to calculate progress. Episodes in the future or without an air date are ignored.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param hidden Include any hidden seasons.
     * @param specials Include specials as season 0.
     * @param countSpecials Count specials in the overall stats (only applies if specials are included)
     * @param lastActivity By default, the last_episode and next_episode are calculated using the last aired episode the
     * user has watched, even if they've watched older episodes more recently. To use their last watched episode for
     * these calculations, set the last_activity flag to collected or watched respectively.
     */
    @GET("shows/{id}/progress/collection")
    Call<BaseShow> collectedProgress(
            @Path("id") String showId,
            @Query("hidden") Boolean hidden,
            @Query("specials") Boolean specials,
            @Query("count_specials") Boolean countSpecials,
            @Query("last_activity") ProgressLastActivity lastActivity,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     *
     * Returns watched progress for show including details on all seasons and episodes. The {@code next_episode} will be
     * the next episode the user should watch, if there are no upcoming episodes it will be set to {@code null}.
     * If not {@code null}, the {@code reset_at} date is when the user started re-watching the show. Your app can adjust
     * the progress by ignoring episodes with a {@code last_watched_at} prior to the {@code reset_at}.
     *
     * <p>By default, any hidden seasons will be removed from the response and stats. To include these and adjust the
     * completion stats, set the {@code hidden} flag to {@code true}.
     *
     * <p>By default, specials will be excluded from the response. Set the {@code specials} flag to {@code true} to
     * include season 0 and adjust the stats accordingly. If you'd like to include specials, but not adjust the stats,
     * set {@code count_specials} to {@code false}.
     *
     * <p>By default, the {@code last_episode} and {@code next_episode} are calculated using the last {@code aired}
     * episode the user has watched, even if they've watched older episodes more recently. To use their last watched
     * episode for these calculations, set the {@code last_activity} flag to {@code watched}.
     *
     * <b>Note:</b>
     *
     * <p>Only aired episodes are used to calculate progress. Episodes in the future or without an air date are ignored.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param hidden Include any hidden seasons.
     * @param specials Include specials as season 0.
     * @param countSpecials Count specials in the overall stats (only applies if specials are included)
     * @param lastActivity By default, the last_episode and next_episode are calculated using the last aired episode the
     * user has watched, even if they've watched older episodes more recently. To use their last watched episode for
     * these calculations, set the last_activity flag to collected or watched respectively.
     */
    @GET("shows/{id}/progress/watched")
    Call<BaseShow> watchedProgress(
            @Path("id") String showId,
            @Query("hidden") Boolean hidden,
            @Query("specials") Boolean specials,
            @Query("count_specials") Boolean countSpecials,
            @Query("last_activity") ProgressLastActivity lastActivity,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Returns all actors, directors, writers, and producers for a show.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("shows/{id}/people")
    Call<Credits> people(
            @Path("id") String showId
    );

    /**
     * Returns rating (between 0 and 10) and distribution for a show.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("shows/{id}/ratings")
    Call<Ratings> ratings(
            @Path("id") String showId
    );

    /**
     * Returns lots of show stats.
     */
    @GET("shows/{id}/stats")
    Call<Stats> stats(
            @Path("id") String showId
    );

    /**
     * Returns related and similar shows.
     */
    @GET("shows/{id}/related")
    Call<List<Show>> related(
            @Path("id") String showId,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

}
