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
import com.uwetrottmann.trakt5.entities.UserSlug;
import com.uwetrottmann.trakt5.entities.WatchlistedEpisode;
import com.uwetrottmann.trakt5.entities.WatchlistedSeason;
import com.uwetrottmann.trakt5.enums.Extended;
import com.uwetrottmann.trakt5.enums.HistoryType;
import com.uwetrottmann.trakt5.enums.PlaybackType;
import com.uwetrottmann.trakt5.enums.RatingsFilter;
import org.threeten.bp.OffsetDateTime;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import javax.annotation.Nonnull;
import java.util.List;

public interface Sync {

    /**
     * <b>OAuth Required</b>
     * <p>
     * This method is a useful first step in the syncing process. We recommended caching these dates locally, then you
     * can compare to know exactly what data has changed recently. This can greatly optimize your syncs so you don't
     * pull down a ton of data only to see nothing has actually changed.
     */
    @GET("sync/last_activities")
    Call<LastActivities> lastActivities();

    /**
     * <b>OAuth Required</b>
     * <p>
     * Get all movies in a user's library (formerly collection). A collected item indicates availability to watch
     * digitally or on physical media.
     *
     * @deprecated Use {@link #collectionMovies(int, int, Extended)} instead.
     */
    @Deprecated
    @GET("sync/collection/movies")
    Call<List<BaseMovie>> collectionMovies(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Like {@link Users#collectionMovies(UserSlug, int, int, Extended)}.
     */
    @GET("sync/collection/movies")
    Call<List<BaseMovie>> collectionMovies(
            @Query("page") int page,
            @Query("limit") int limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Get all shows in a user's library (formerly collection). A collected item indicates availability to watch
     * digitally or on physical media.
     *
     * @deprecated Use {@link #collectionShows(int, int, Extended)} instead.
     */
    @Deprecated
    @GET("sync/collection/shows")
    Call<List<BaseShow>> collectionShows(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Like {@link Users#collectionShows(UserSlug, int, int, Extended)}.
     */
    @GET("sync/collection/shows")
    Call<List<BaseShow>> collectionShows(
            @Query("page") int page,
            @Query("limit") int limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Add one or more items to a user's library (formerly collection) including the format of the item.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/collection")
    Call<SyncResponse> addItemsToCollection(
            @Body SyncItems items
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Remove one or more items from a user's library (formerly collection).
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/collection/remove")
    Call<SyncResponse> deleteItemsFromCollection(
            @Body SyncItems items
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Returns all movies a user has watched.
     */
    @GET("sync/watched/movies")
    Call<List<BaseMovie>> watchedMovies(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Whenever a scrobble is paused, the playback progress is saved. Use this progress to sync up playback across
     * different media centers or apps. For example, you can start watching a movie in a media center, stop it, then
     * resume on your tablet from the same spot. Each item will have the progress percentage between 0 and 100.
     * <p>
     * Use {@link #playback(PlaybackType, OffsetDateTime, OffsetDateTime, Integer, Integer)} to specify a type to only
     * get movies or episodes.
     * <p>
     * By default, all results will be returned. Pagination is optional and can be used for something like an "on deck"
     * feature, or if you only need a limited data set.
     * <p>
     * Note: Trakt only saves playback progress for the last 6 months.
     *
     * @see #playback(PlaybackType, OffsetDateTime, OffsetDateTime, Integer, Integer)
     */
    @GET("sync/playback")
    Call<List<PlaybackResponse>> playback(
            @Query("start_at") OffsetDateTime startAt,
            @Query("end_at") OffsetDateTime endAt,
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Like {@link #playback(OffsetDateTime, OffsetDateTime, Integer, Integer)}, but allows to specify a type to only
     * get movies or episodes.
     *
     * @see #playback(OffsetDateTime, OffsetDateTime, Integer, Integer)
     */
    @GET("sync/playback/{type}")
    Call<List<PlaybackResponse>> playback(
            @Path("type") PlaybackType type,
            @Query("start_at") OffsetDateTime startAt,
            @Query("end_at") OffsetDateTime endAt,
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Returns all playbacks;
     *
     * @deprecated Use {@link #playback(OffsetDateTime, OffsetDateTime, Integer, Integer)} instead.
     */
    @Deprecated
    @GET("sync/playback")
    Call<List<PlaybackResponse>> getPlayback(
            @Query("limit") Integer limit
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Returns all playbacks;
     *
     * @deprecated Use {@link #playback(PlaybackType, OffsetDateTime, OffsetDateTime, Integer, Integer)} instead.
     */
    @Deprecated
    @GET("sync/playback/episodes")
    Call<List<PlaybackResponse>> getPlaybackEpisodes(
            @Query("limit") Integer limit
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Returns all playbacks;
     *
     * @deprecated Use {@link #playback(PlaybackType, OffsetDateTime, OffsetDateTime, Integer, Integer)} instead.
     */
    @Deprecated
    @GET("sync/playback/movies")
    Call<List<PlaybackResponse>> getPlaybackMovies(
            @Query("limit") Integer limit
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Remove a playback item from a user's playback progress list. A 404 will be returned if the id is invalid.
     * <p>
     * <a href="https://trakt.docs.apiary.io/#reference/sync/remove-playback/remove-a-playback-item">Online
     * documentation</a>
     */
    @DELETE("sync/playback/{id}")
    Call<Void> removePlayback(
            @Path("id") long id
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Returns all shows a user has watched.
     */
    @GET("sync/watched/shows")
    Call<List<BaseShow>> watchedShows(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Like {@link Users#history(UserSlug, Integer, Integer, Extended, OffsetDateTime, OffsetDateTime)}.
     *
     * @see Sync#history(HistoryType, Integer, Integer, Extended, OffsetDateTime, OffsetDateTime)
     */
    @GET("sync/history")
    Call<List<HistoryEntry>> history(
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended,
            @Query("start_at") OffsetDateTime startAt,
            @Query("end_at") OffsetDateTime endAt
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Like {@link #history(Integer, Integer, Extended, OffsetDateTime, OffsetDateTime)}, but allows to set a type to
     * only return movies or episodes.
     *
     * @see Users#history(UserSlug, HistoryType, Integer, Integer, Extended, OffsetDateTime, OffsetDateTime)
     * @see Sync#history(Integer, Integer, Extended, OffsetDateTime, OffsetDateTime)
     */
    @GET("sync/history/{type}")
    Call<List<HistoryEntry>> history(
            @Path("type") HistoryType type,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended,
            @Query("start_at") OffsetDateTime startAt,
            @Query("end_at") OffsetDateTime endAt
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Like
     * {@link Users#history(UserSlug, HistoryType, int, Integer, Integer, Extended, OffsetDateTime, OffsetDateTime)}.
     */
    @GET("sync/history/{type}/{id}")
    Call<List<HistoryEntry>> history(
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
     * <p>
     * Add items to a user's watch history. Accepts shows, seasons, episodes and movies. If only a show is passed,
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
     * <p>
     * Remove items from a user's watch history including all watches, scrobbles, and checkins. Accepts shows, seasons,
     * episodes and movies. If only a show is passed, assumes all seasons are to be removed from history. Same for
     * seasons.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/history/remove")
    Call<SyncResponse> deleteItemsFromWatchedHistory(
            @Body SyncItems items
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Get a user's ratings filtered by movies. You can filter for a specific rating between 1 and 10.
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
     * <p>
     * Get a user's ratings filtered by shows. You can filter for a specific rating between 1 and 10.
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
     * <p>
     * Get a user's ratings filtered by seasons. You can filter for a specific rating between 1 and 10.
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
     * <p>
     * Get a user's ratings filtered by episodes. You can filter for a specific rating between 1 and 10.
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
     * <p>
     * Rate one or more items.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/ratings")
    Call<SyncResponse> addRatings(
            @Body SyncItems items
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Delete ratings for one or more items.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/ratings/remove")
    Call<SyncResponse> deleteRatings(
            @Body SyncItems items
    );


    /**
     * <b>OAuth Required</b>
     * <p>
     * Like {@link Users#watchlistMovies(UserSlug, Extended)}.
     */
    @GET("sync/watchlist/movies")
    Call<List<BaseMovie>> watchlistMovies(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Like {@link Users#watchlistMovies(UserSlug, Integer, Integer, Extended)}.
     */
    @GET("sync/watchlist/movies")
    Call<List<BaseMovie>> watchlistMovies(
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Like {@link Users#watchlistMovies(UserSlug, String, String, Integer, Integer, Extended)}.
     */
    @GET("sync/watchlist/movies/{sort_by}/{sort_how}")
    Call<List<BaseMovie>> watchlistMovies(
            @Nonnull @Path("sort_by") String sortBy,
            @Nonnull @Path("sort_how") String sortHow,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Like {@link Users#watchlistShows(UserSlug, Extended)}.
     */
    @GET("sync/watchlist/shows")
    Call<List<BaseShow>> watchlistShows(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Like {@link Users#watchlistShows(UserSlug, Integer, Integer, Extended)}.
     */
    @GET("sync/watchlist/shows")
    Call<List<BaseShow>> watchlistShows(
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Like {@link Users#watchlistShows(UserSlug, String, String, Integer, Integer, Extended)}.
     */
    @GET("sync/watchlist/shows/{sort_by}/{sort_how}")
    Call<List<BaseShow>> watchlistShows(
            @Nonnull @Path("sort_by") String sortBy,
            @Nonnull @Path("sort_how") String sortHow,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Like {@link Users#watchlistSeasons(UserSlug, Extended)}.
     */
    @GET("sync/watchlist/seasons")
    Call<List<WatchlistedSeason>> watchlistSeasons(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Like {@link Users#watchlistSeasons(UserSlug, Integer, Integer, Extended)}.
     */
    @GET("sync/watchlist/seasons")
    Call<List<WatchlistedSeason>> watchlistSeasons(
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Like {@link Users#watchlistSeasons(UserSlug, String, String, Integer, Integer, Extended)}.
     */
    @GET("sync/watchlist/seasons/{sort_by}/{sort_how}")
    Call<List<WatchlistedSeason>> watchlistSeasons(
            @Nonnull @Path("sort_by") String sortBy,
            @Nonnull @Path("sort_how") String sortHow,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Like {@link Users#watchlistEpisodes(UserSlug, Extended)}.
     */
    @GET("sync/watchlist/episodes")
    Call<List<WatchlistedEpisode>> watchlistEpisodes(
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Like {@link Users#watchlistEpisodes(UserSlug, Integer, Integer, Extended)}.
     */
    @GET("sync/watchlist/episodes")
    Call<List<WatchlistedEpisode>> watchlistEpisodes(
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Like {@link Users#watchlistEpisodes(UserSlug, String, String, Integer, Integer, Extended)}.
     */
    @GET("sync/watchlist/episodes/{sort_by}/{sort_how}")
    Call<List<WatchlistedEpisode>> watchlistEpisodes(
            @Nonnull @Path("sort_by") String sortBy,
            @Nonnull @Path("sort_how") String sortHow,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Add one of more items to a user's watchlist.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/watchlist")
    Call<SyncResponse> addItemsToWatchlist(
            @Body SyncItems items
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Delete one or more items from a user's watchlist.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/watchlist/remove")
    Call<SyncResponse> deleteItemsFromWatchlist(
            @Body SyncItems items
    );

}
