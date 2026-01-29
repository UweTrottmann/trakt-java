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
import com.uwetrottmann.trakt5.entities.Followed;
import com.uwetrottmann.trakt5.entities.Follower;
import com.uwetrottmann.trakt5.entities.Friend;
import com.uwetrottmann.trakt5.entities.HistoryEntry;
import com.uwetrottmann.trakt5.entities.ListEntry;
import com.uwetrottmann.trakt5.entities.ListItemRank;
import com.uwetrottmann.trakt5.entities.ListReorderResponse;
import com.uwetrottmann.trakt5.entities.NoteResponse;
import com.uwetrottmann.trakt5.entities.RatedEpisode;
import com.uwetrottmann.trakt5.entities.RatedMovie;
import com.uwetrottmann.trakt5.entities.RatedSeason;
import com.uwetrottmann.trakt5.entities.RatedShow;
import com.uwetrottmann.trakt5.entities.Settings;
import com.uwetrottmann.trakt5.entities.SyncItems;
import com.uwetrottmann.trakt5.entities.SyncResponse;
import com.uwetrottmann.trakt5.entities.TraktList;
import com.uwetrottmann.trakt5.entities.User;
import com.uwetrottmann.trakt5.entities.UserSlug;
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
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface Users {

    /**
     * <b>OAuth Required</b>
     * <p>
     * Get the user's settings so you can align your app's experience with what they're used to on the Trakt website.
     */
    @GET("users/settings")
    Call<Settings> settings();

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Get a user's profile information. If the user is private, info will only be returned if you send OAuth and are
     * either that user or an approved follower.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}")
    Call<User> profile(
            @Path("username") UserSlug userSlug,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Get all collected movies in a user's collection. A collected item indicates availability to watch digitally or on
     * physical media.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}/collection/movies")
    Call<List<BaseMovie>> collectionMovies(
            @Path("username") UserSlug userSlug,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Get all collected shows in a user's collection. A collected item indicates availability to watch digitally or on
     * physical media.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}/collection/shows")
    Call<List<BaseShow>> collectionShows(
            @Path("username") UserSlug userSlug,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>VIP Only, OAuth Optional</b>
     * <p>
     * Returns the most recently added notes for the user.
     * <p>
     * <a href="https://trakt.docs.apiary.io/#reference/users/notes/get-notes">Online documentation</a>
     */
    @GET("users/{username}/notes/{type}")
    Call<List<NoteResponse>> notes(
            @Path("username") UserSlug userSlug,
            @Path("type") String type,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Returns all custom lists for a user.
     */
    @GET("users/{username}/lists")
    Call<List<TraktList>> lists(
            @Path("username") UserSlug userSlug
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Create a new custom list. The name is the only required field, but the other info is recommended to ask for.
     */
    @POST("users/{username}/lists")
    Call<TraktList> createList(
            @Path("username") UserSlug userSlug,
            @Body TraktList list
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Update a custom list by sending 1 or more parameters. If you update the list name, the original slug will still
     * be retained so existing references to this list won't break.
     */
    @PUT("users/{username}/lists/{id}")
    Call<TraktList> updateList(
            @Path("username") UserSlug userSlug,
            @Path("id") String id,
            @Body TraktList list
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Remove a custom list and all items it contains.
     */
    @DELETE("users/{username}/lists/{id}")
    Call<Void> deleteList(
            @Path("username") UserSlug userSlug,
            @Path("id") String id
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Reorder all custom lists by sending the updated rank of list ids.
     */
    @POST("users/{username}/lists/reorder")
    Call<ListReorderResponse> reorderLists(
            @Path("username") UserSlug userSlug,
            @Body ListItemRank rank
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Get all items on a custom list. Items can be movies, shows, seasons, episodes, or people.
     */
    @GET("users/{username}/lists/{id}/items")
    Call<List<ListEntry>> listItems(
            @Path("username") UserSlug userSlug,
            @Path("id") String id,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Add one or more items to a custom list. Items can be movies, shows, seasons, episodes, or people.
     */
    @POST("users/{username}/lists/{id}/items")
    Call<SyncResponse> addListItems(
            @Path("username") UserSlug userSlug,
            @Path("id") String id,
            @Body SyncItems items
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Remove one or more items from a custom list.
     */
    @POST("users/{username}/lists/{id}/items/remove")
    Call<SyncResponse> deleteListItems(
            @Path("username") UserSlug userSlug,
            @Path("id") String id,
            @Body SyncItems items
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Reorder all items on a list by sending the updated rank of list item ids.
     */
    @POST("users/{username}/lists/{id}/items/reorder")
    Call<ListReorderResponse> reorderListItems(
            @Path("username") UserSlug userSlug,
            @Path("id") String id,
            @Body ListItemRank rank
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * If the user has a private profile, the follow request will require approval (approved_at will be null). If a user
     * is public, they will be followed immediately (approved_at will have a date).
     * <p>
     * Note: If this user is already being followed, a 409 HTTP status code will returned.
     */
    @POST("users/{username}/follow")
    Call<Followed> follow(
            @Path("username") UserSlug userSlug
    );

    /**
     * <b>OAuth Required</b>
     * <p>
     * Unfollow someone you already follow.
     */
    @DELETE("users/{username}/follow")
    Call<Void> unfollow(
            @Path("username") UserSlug userSlug
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Returns all followers including when the relationship began.
     */
    @GET("users/{username}/followers")
    Call<List<Follower>> followers(
            @Path("username") UserSlug userSlug,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Returns all user's they follow including when the relationship began.
     */
    @GET("users/{username}/following")
    Call<List<Follower>> following(
            @Path("username") UserSlug userSlug,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Returns all friends for a user including when the relationship began. Friendship is a 2 way relationship where
     * each user follows the other.
     */
    @GET("users/{username}/friends")
    Call<List<Friend>> friends(
            @Path("username") UserSlug userSlug,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Returns movies and episodes that a user has watched, sorted by most recent.
     * <p>
     * The {@code id} uniquely identifies each history event and can be used to remove events individually using the
     * {@code POST /sync/history/remove method}. The action will be set to {@code scrobble}, {@code checkin}, or
     * {@code watch}.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}/history")
    Call<List<HistoryEntry>> history(
            @Path("username") UserSlug userSlug,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended,
            @Query("start_at") OffsetDateTime startAt,
            @Query("end_at") OffsetDateTime endAt
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Returns movies or episodes that a user has watched, sorted by most recent.
     * <p>
     * The {@code id} uniquely identifies each history event and can be used to remove events individually using the
     * {@code POST /sync/history/remove method}. The action will be set to {@code scrobble}, {@code checkin}, or
     * {@code watch}.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}/history/{type}")
    Call<List<HistoryEntry>> history(
            @Path("username") UserSlug userSlug,
            @Path("type") HistoryType type,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended,
            @Query("start_at") OffsetDateTime startAt,
            @Query("end_at") OffsetDateTime endAt
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Returns the history for just the specified item. For example, {@code /history/movies/12601} would return all
     * watches for TRON: Legacy and {@code /history/shows/1388} would return all watched episodes for Breaking Bad. If
     * an invalid {@code id} is sent, a 404 error will be returned. If the {@code id} is valid, but there is no history,
     * an empty array will be returned.
     * <p>
     * The {@code id} uniquely identifies each history event and can be used to remove events individually using the
     * {@code POST /sync/history/remove method}. The action will be set to {@code scrobble}, {@code checkin}, or
     * {@code watch}.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}/history/{type}/{id}")
    Call<List<HistoryEntry>> history(
            @Path("username") UserSlug userSlug,
            @Path("type") HistoryType type,
            @Path("id") int id,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended,
            @Query("start_at") OffsetDateTime startAt,
            @Query("end_at") OffsetDateTime endAt
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Get a user's ratings filtered by movies. You can filter for a specific rating between 1 and 10.
     *
     * @param userSlug Example: "sean".
     * @param filter   Filter for a specific rating.
     */
    @GET("users/{username}/ratings/movies{rating}")
    Call<List<RatedMovie>> ratingsMovies(
            @Path("username") UserSlug userSlug,
            @Path(value = "rating", encoded = true) RatingsFilter filter,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Get a user's ratings filtered by shows. You can filter for a specific rating between 1 and 10.
     *
     * @param userSlug Example: "sean".
     * @param filter   Filter for a specific rating.
     */
    @GET("users/{username}/ratings/shows{rating}")
    Call<List<RatedShow>> ratingsShows(
            @Path("username") UserSlug userSlug,
            @Path(value = "rating", encoded = true) RatingsFilter filter,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Get a user's ratings filtered by seasons. You can filter for a specific rating between 1 and 10.
     *
     * @param userSlug Example: "sean".
     * @param filter   Filter for a specific rating.
     */
    @GET("users/{username}/ratings/seasons{rating}")
    Call<List<RatedSeason>> ratingsSeasons(
            @Path("username") UserSlug userSlug,
            @Path(value = "rating", encoded = true) RatingsFilter filter,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Get a user's ratings filtered by episodes. You can filter for a specific rating between 1 and 10.
     *
     * @param userSlug Example: "sean".
     * @param filter   Filter for a specific rating.
     */
    @GET("users/{username}/ratings/episodes{rating}")
    Call<List<RatedEpisode>> ratingsEpisodes(
            @Path("username") UserSlug userSlug,
            @Path(value = "rating", encoded = true) RatingsFilter filter,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Returns all items in a user's watchlist filtered by movies. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("users/{username}/watchlist/movies")
    Call<List<BaseMovie>> watchlistMovies(
            @Path("username") UserSlug userSlug,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Returns all items in a user's watchlist filtered by shows. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("users/{username}/watchlist/shows")
    Call<List<BaseShow>> watchlistShows(
            @Path("username") UserSlug userSlug,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Returns all items in a user's watchlist filtered by seasons. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("users/{username}/watchlist/seasons")
    Call<List<WatchlistedSeason>> watchlistSeasons(
            @Path("username") UserSlug userSlug,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Returns all items in a user's watchlist filtered by episodes. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("users/{username}/watchlist/episodes")
    Call<List<WatchlistedEpisode>> watchlistEpisodes(
            @Path("username") UserSlug userSlug,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Returns all movies or shows a user has watched sorted by most plays.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}/watched/movies")
    Call<List<BaseMovie>> watchedMovies(
            @Path("username") UserSlug userSlug,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * <b>OAuth Optional</b>
     * <p>
     * Returns all movies or shows a user has watched sorted by most plays.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}/watched/shows")
    Call<List<BaseShow>> watchedShows(
            @Path("username") UserSlug userSlug,
            @Query(value = "extended", encoded = true) Extended extended
    );

}
