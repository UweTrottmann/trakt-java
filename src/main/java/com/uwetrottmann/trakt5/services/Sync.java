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

import com.uwetrottmann.trakt5.entities.BaseMovie;
import com.uwetrottmann.trakt5.entities.BaseShow;
import com.uwetrottmann.trakt5.entities.HistoryEntry;
import com.uwetrottmann.trakt5.entities.LastActivities;
import com.uwetrottmann.trakt5.entities.PlaybackResponse;
import com.uwetrottmann.trakt5.entities.RatedEpisode;
import com.uwetrottmann.trakt5.entities.RatedMovie;
import com.uwetrottmann.trakt5.entities.RatedSeason;
import com.uwetrottmann.trakt5.entities.RatedShow;
import com.uwetrottmann.trakt5.entities.SyncItems;
import com.uwetrottmann.trakt5.entities.SyncResponse;
import com.uwetrottmann.trakt5.entities.WatchlistedEpisode;
import com.uwetrottmann.trakt5.entities.WatchlistedSeason;
import com.uwetrottmann.trakt5.enums.Extended;
import com.uwetrottmann.trakt5.enums.HistoryType;
import com.uwetrottmann.trakt5.enums.RatingsFilter;
import org.threeten.bp.OffsetDateTime;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface Sync {

    /**
     * <b>OAuth Required</b>
     *
     * <p> This method is a useful first step in the syncing process. We recommended caching these dates locally, then
     * you can compare to know exactly what data has changed recently. This can greatly optimize your syncs so you don't
     * pull down a ton of data only to see nothing has actually changed.
     */
    @GET("sync/last_activities")
    Call<LastActivities> lastActivities();

    /**
     * <b>OAuth Required</b>
     *
     * <p> Get all collected movies in a user's collection. A collected item indicates availability to watch digitally
     * or on physical media.
     */
    @GET("sync/collection/movies")
    Call<List<BaseMovie>> collectionMovies(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Get all collected shows in a user's collection. A collected item indicates availability to watch digitally or
     * on physical media.
     */
    @GET("sync/collection/shows")
    Call<List<BaseShow>> collectionShows(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Add one or more items to a user's collection including the format of the item.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/collection")
    Call<SyncResponse> addItemsToCollection(
            @Body SyncItems items
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Remove one or more items from a user's collection.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/collection/remove")
    Call<SyncResponse> deleteItemsFromCollection(
            @Body SyncItems items
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all movies a user has watched.
     */
    @GET("sync/watched/movies")
    Call<List<BaseMovie>> watchedMovies(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all playbacks;
     */
    @GET("sync/playback")
    Call<List<PlaybackResponse>> getPlayback(
            @Query("limit") Integer limit
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all playbacks;
     */
    @GET("sync/playback/episodes")
    Call<List<PlaybackResponse>> getPlaybackEpisodes(
            @Query("limit") Integer limit
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all playbacks;
     */
    @GET("sync/playback/movies")
    Call<List<PlaybackResponse>> getPlaybackMovies(
            @Query("limit") Integer limit
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Remove a playback item from a user's playback progress list. A 404 will be returned if the id is invalid.
     * <p>
     * <a href="https://trakt.docs.apiary.io/#reference/sync/remove-playback/remove-a-playback-item">Online documentation</a>
     */
    @DELETE("sync/playback/{id}")
    Call<Void> removePlayback(
            @Path("id") long id
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all shows a user has watched.
     */
    @GET("sync/watched/shows")
    Call<List<BaseShow>> watchedShows(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns movies and episodes that the a user has watched, sorted by most recent.
     *
     * <p>The {@code id} uniquely identifies each history event and can be used to remove events individually using the
     * {@code POST /sync/history/remove method}. The action will be set to {@code scrobble}, {@code checkin}, or {@code
     * watch}.
     */
    @GET("sync/history")
    Call<List<HistoryEntry>> getHistory(
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended,
            @Query("start_at") OffsetDateTime startAt,
            @Query("end_at") OffsetDateTime endAt
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns movies or episodes (depending on the <code>type</code>) that the a user has watched,
     * sorted by most recent.
     *
     * <p>The {@code id} uniquely identifies each history event and can be used to remove events individually using the
     * {@code POST /sync/history/remove method}. The action will be set to {@code scrobble}, {@code checkin}, or {@code
     * watch}.
     */
    @GET("sync/history/{type}")
    Call<List<HistoryEntry>> getHistory(
            @Path("type") HistoryType type,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended,
            @Query("start_at") OffsetDateTime startAt,
            @Query("end_at") OffsetDateTime endAt
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p>Returns the history for just the specified item. For example, {@code /sync/history/movies/12601} would return
     * all watches for TRON: Legacy and {@code /sync/history/shows/1388} would return all watched episodes
     * for Breaking Bad. If an invalid {@code id} is sent, a 404 error will be returned. If the {@code id} is valid,
     * but there is no history, an empty array will be returned.
     *
     * <p>The {@code id} uniquely identifies each history event and can be used to remove events individually using the
     * {@code POST /sync/history/remove method}. The action will be set to {@code scrobble}, {@code checkin}, or {@code
     * watch}.
     */
    @GET("sync/history/{type}/{id}")
    Call<List<HistoryEntry>> getHistory(
            @Path("type") HistoryType type,
            @Path("id") int id,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended,
            @Query("start_at") OffsetDateTime startAt,
            @Query("end_at") OffsetDateTime endAt
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Add items to a user's watch history. Accepts shows, seasons, episodes and movies. If only a show is passed,
     * assumes all seasons are to be marked watched. Same for seasons. Send a <code>watched_at</code> UTC datetime to
     * mark items as watched in the past. This is useful for syncing past watches from a media center.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/history")
    Call<SyncResponse> addItemsToWatchedHistory(
            @Body SyncItems items
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Remove items from a user's watch history including all watches, scrobbles, and checkins. Accepts shows,
     * seasons, episodes and movies. If only a show is passed, assumes all seasons are to be removed from history. Same
     * for seasons.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/history/remove")
    Call<SyncResponse> deleteItemsFromWatchedHistory(
            @Body SyncItems items
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Get a user's ratings filtered by movies. You can filter for a specific rating between 1 and 10.
     *
     * @param filter Filter for a specific rating.
     * @param page   Number of page of results to be returned. If {@code null} but {@code limit} is provided defaults to
     *               1, otherwise items are paginated.
     * @param limit  Number of results to return per page. If {@code null} but {@code page} is provided defaults to 10,
     *               otherwise all items are returned.
     */
    @GET("sync/ratings/movies{rating}")
    Call<List<RatedMovie>> ratingsMovies(
            @Path(value = "rating", encoded = true) RatingsFilter filter,
            @Query(value = "extended", encoded = true) Extended extended,
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Get a user's ratings filtered by shows. You can filter for a specific rating between 1 and 10.
     *
     * @param filter Filter for a specific rating.
     * @param page   Number of page of results to be returned. If {@code null} but {@code limit} is provided defaults to
     *               1, otherwise items are not paginated.
     * @param limit  Number of results to return per page. If {@code null} but {@code page} is provided defaults to 10,
     *               otherwise all items are returned.
     */
    @GET("sync/ratings/shows{rating}")
    Call<List<RatedShow>> ratingsShows(
            @Path(value = "rating", encoded = true) RatingsFilter filter,
            @Query(value = "extended", encoded = true) Extended extended,
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Get a user's ratings filtered by seasons. You can filter for a specific rating between 1 and 10.
     *
     * @param filter Filter for a specific rating.
     * @param page   Number of page of results to be returned. If {@code null} but {@code limit} is provided defaults to
     *               1, otherwise items are paginated.
     * @param limit  Number of results to return per page. If {@code null} but {@code page} is provided defaults to 10,
     *               otherwise all items are returned.
     */
    @GET("sync/ratings/seasons{rating}")
    Call<List<RatedSeason>> ratingsSeasons(
            @Path(value = "rating", encoded = true) RatingsFilter filter,
            @Query(value = "extended", encoded = true) Extended extended,
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Get a user's ratings filtered by episodes. You can filter for a specific rating between 1 and 10.
     *
     * @param filter Filter for a specific rating.
     * @param page   Number of page of results to be returned. If {@code null} but {@code limit} is provided defaults to
     *               1, otherwise items are paginated.
     * @param limit  Number of results to return per page. If {@code null} but {@code page} is provided defaults to 10,
     *               otherwise all items are returned.
     */
    @GET("sync/ratings/episodes{rating}")
    Call<List<RatedEpisode>> ratingsEpisodes(
            @Path(value = "rating", encoded = true) RatingsFilter filter,
            @Query(value = "extended", encoded = true) Extended extended,
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Rate one or more items.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/ratings")
    Call<SyncResponse> addRatings(
            @Body SyncItems items
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Delete ratings for one or more items.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/ratings/remove")
    Call<SyncResponse> deleteRatings(
            @Body SyncItems items
    );


    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all items in a user's watchlist filtered by movies. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("sync/watchlist/movies")
    Call<List<BaseMovie>> watchlistMovies(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all items in a user's watchlist filtered by shows. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("sync/watchlist/shows")
    Call<List<BaseShow>> watchlistShows(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all items in a user's watchlist filtered by seasons. When an item is watched, it will be
     * automatically removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("sync/watchlist/seasons")
    Call<List<WatchlistedSeason>> watchlistSeasons(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all items in a user's watchlist filtered by episodes. When an item is watched, it will be
     * automatically removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("sync/watchlist/episodes")
    Call<List<WatchlistedEpisode>> watchlistEpisodes(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Add one of more items to a user's watchlist.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/watchlist")
    Call<SyncResponse> addItemsToWatchlist(
            @Body SyncItems items
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Delete one or more items from a user's watchlist.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/watchlist/remove")
    Call<SyncResponse> deleteItemsFromWatchlist(
            @Body SyncItems items
    );

}
