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
import com.uwetrottmann.trakt5.enums.ExtendedMoviesWatched;
import com.uwetrottmann.trakt5.enums.ExtendedShowsWatched;
import com.uwetrottmann.trakt5.enums.HistoryType;
import com.uwetrottmann.trakt5.enums.RatingsFilter;
import com.uwetrottmann.trakt5.enums.Specials;
import org.threeten.bp.OffsetDateTime;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

import static com.uwetrottmann.trakt5.TraktV2.QUERY_PARAM_END_AT;
import static com.uwetrottmann.trakt5.TraktV2.QUERY_PARAM_EXTENDED;
import static com.uwetrottmann.trakt5.TraktV2.QUERY_PARAM_EXTENDED_MIN;
import static com.uwetrottmann.trakt5.TraktV2.QUERY_PARAM_LIMIT;
import static com.uwetrottmann.trakt5.TraktV2.QUERY_PARAM_PAGE;
import static com.uwetrottmann.trakt5.TraktV2.QUERY_PARAM_SPECIALS;
import static com.uwetrottmann.trakt5.TraktV2.QUERY_PARAM_START_AT;

public interface Users {

    String PATH_USERNAME = "username";
    String PATH_TYPE = "type";
    String PATH_ID = "id";
    String PATH_SORT_BY = "sort_by";
    String PATH_SORT_HOW = "sort_how";
    String PATH_RATING = "rating";

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Get the user's settings so you can align your app's experience with what they're used to on the Trakt website.
     */
    @GET("users/settings")
    Call<Settings> settings();

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Get a user's profile information. If the user is private, info will only be returned if you send OAuth and are
     * either that user or an approved follower.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{" + PATH_USERNAME + "}")
    Call<User> profile(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Get all movies in a user's library (formerly collection). A collected item indicates availability to watch
     * digitally or on physical media.
     *
     * @param userSlug Example: "sean".
     * @deprecated Use {@link #collectionMovies(UserSlug, int, int, Extended)} instead.
     */
    @Deprecated
    @GET("users/{" + PATH_USERNAME + "}/collection/movies")
    Call<List<BaseMovie>> collectionMovies(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Get all movies in a user's library (formerly collection).
     *
     * @param userSlug Example: "sean".
     * @param page     Number of page of results to be returned.
     * @param limit    Number of results to return per page.
     * @see Sync#collectionMovies(int, int, Extended)
     */
    @GET("users/{" + PATH_USERNAME + "}/collection/movies")
    Call<List<BaseMovie>> collectionMovies(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(QUERY_PARAM_PAGE) int page,
            @Query(QUERY_PARAM_LIMIT) int limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Get all shows in a user's library (formerly collection). A collected item indicates availability to watch
     * digitally or on physical media.
     *
     * @param userSlug Example: "sean".
     * @deprecated Use {@link #collectionShows(UserSlug, int, int, Extended)} instead.
     */
    @Deprecated
    @GET("users/{" + PATH_USERNAME + "}/collection/shows")
    Call<List<BaseShow>> collectionShows(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Get all shows in a user's library (formerly collection).
     *
     * @param userSlug Example: "sean".
     * @param page     Number of page of results to be returned.
     * @param limit    Number of results to return per page.
     * @see Sync#collectionShows(int, int, Extended)
     */
    @GET("users/{" + PATH_USERNAME + "}/collection/shows")
    Call<List<BaseShow>> collectionShows(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(QUERY_PARAM_PAGE) int page,
            @Query(QUERY_PARAM_LIMIT) int limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns the most recently added notes for the user.
     * <p>
     * <a href="https://trakt.docs.apiary.io/#reference/users/notes/get-notes">Online documentation</a>
     */
    @GET("users/{" + PATH_USERNAME + "}/notes/{" + PATH_TYPE + "}")
    Call<List<NoteResponse>> notes(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(PATH_TYPE) String type,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns all personal lists for a user.
     *
     * @deprecated Use {@link #lists(UserSlug, Integer, Integer, Extended)} instead.
     */
    @Deprecated
    @GET("users/{" + PATH_USERNAME + "}/lists")
    Call<List<TraktList>> lists(
            @Path(PATH_USERNAME) UserSlug userSlug
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns all personal lists for a user. Use the {@link #listItems(UserSlug, String, int, int, Extended)} method to
     * get the actual items a specific list contains.
     */
    @GET("users/{" + PATH_USERNAME + "}/lists")
    Call<List<TraktList>> lists(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Create a new custom list. The name is the only required field, but the other info is recommended to ask for.
     */
    @POST("users/{" + PATH_USERNAME + "}/lists")
    Call<TraktList> createList(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Body TraktList list
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Update a custom list by sending 1 or more parameters. If you update the list name, the original slug will still
     * be retained so existing references to this list won't break.
     */
    @PUT("users/{" + PATH_USERNAME + "}/lists/{" + PATH_ID + "}")
    Call<TraktList> updateList(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(PATH_ID) String id,
            @Body TraktList list
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Remove a custom list and all items it contains.
     */
    @DELETE("users/{" + PATH_USERNAME + "}/lists/{" + PATH_ID + "}")
    Call<Void> deleteList(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(PATH_ID) String id
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Reorder all custom lists by sending the updated rank of list ids.
     */
    @POST("users/{" + PATH_USERNAME + "}/lists/reorder")
    Call<ListReorderResponse> reorderLists(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Body ListItemRank rank
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Get all items on a custom list. Items can be movies, shows, seasons, episodes, or people.
     *
     * @deprecated Use {@link #listItems(UserSlug, String, int, int, Extended)} instead.
     */
    @Deprecated
    @GET("users/{" + PATH_USERNAME + "}/lists/{" + PATH_ID + "}/items")
    Call<List<ListEntry>> listItems(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(PATH_ID) String id,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Get all items on a personal list. Items can be a movie, show, season, episode, or person. Use
     * {@link #listItems(UserSlug, String, String, int, int, Extended)} to specify the type parameter with a single
     * value or comma-delimited string for multiple item types.
     * <p>
     * Notes: Each list item contains a notes field with text entered by the user.
     * <p>
     * Sorting: Default sorting is based on the list defaults and sent in the X-Sort-By and X-Sort-How headers. Use
     * {@link #listItems(UserSlug, String, String, String, String, int, int, Extended)} to specify a custom sort order.
     */
    @GET("users/{" + PATH_USERNAME + "}/lists/{" + PATH_ID + "}/items")
    Call<List<ListEntry>> listItems(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(PATH_ID) String id,
            @Query(QUERY_PARAM_PAGE) int page,
            @Query(QUERY_PARAM_LIMIT) int limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * Like {@link #listItems(UserSlug, String, int, int, Extended)}, but you can specify a sort order.
     * <p>
     * The specified order will be sent in the X-Applied-Sort-By and X-Applied-Sort-How headers.
     * <p>
     * Some sort_by options are VIP Only including imdb_rating, tmdb_rating, rt_tomatometer, rt_audience, metascore,
     * votes, imdb_votes, and tmdb_votes. If sent for a non VIP, the items will fall back to rank.
     *
     * @param sortBy  Sort by a specific property. Possible values: rank, added, title, released , runtime, popularity,
     *                random, percentage, imdb_rating, tmdb_rating, rt_tomatometer, rt_audience, metascore, votes,
     *                imdb_votes, tmdb_votes, my_rating, watched, collected.
     * @param sortHow Sort direction. Possible values: asc, desc.
     */
    @GET("users/{" + PATH_USERNAME + "}/lists/{" + PATH_ID + "}/items/{" + PATH_SORT_BY + "}/{" + PATH_SORT_HOW + "}")
    Call<List<ListEntry>> listItems(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(PATH_ID) String id,
            @Path(PATH_SORT_BY) String sortBy,
            @Path(PATH_SORT_HOW) String sortHow,
            @Query(QUERY_PARAM_PAGE) int page,
            @Query(QUERY_PARAM_LIMIT) int limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * Like {@link #listItems(UserSlug, String, int, int, Extended)}, but you can specify the type parameter with a
     * single value or comma-delimited string for multiple item types.
     *
     * @param type Filter for a specific item type. Example: {@code movie,show}. Possible values: movie, show, season,
     *             episode, person.
     */
    @GET("users/{" + PATH_USERNAME + "}/lists/{" + PATH_ID + "}/items/{" + PATH_TYPE + "}")
    Call<List<ListEntry>> listItems(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(PATH_ID) String id,
            @Path(PATH_TYPE) String type,
            @Query(QUERY_PARAM_PAGE) int page,
            @Query(QUERY_PARAM_LIMIT) int limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * Like {@link #listItems(UserSlug, String, String, int, int, Extended)}, but you can specify a sort order.
     * <p>
     * The specified order will be sent in the X-Applied-Sort-By and X-Applied-Sort-How headers.
     * <p>
     * Some sort_by options are VIP Only including imdb_rating, tmdb_rating, rt_tomatometer, rt_audience, metascore,
     * votes, imdb_votes, and tmdb_votes. If sent for a non VIP, the items will fall back to rank.
     *
     * @param type    Filter for a specific item type. Example: {@code movie,show}. Possible values: movie, show,
     *                season, episode, person.
     * @param sortBy  Sort by a specific property. Possible values: rank, added, title, released, runtime, popularity,
     *                random, percentage, imdb_rating, tmdb_rating, rt_tomatometer, rt_audience, metascore, votes,
     *                imdb_votes, tmdb_votes, my_rating, watched, collected.
     * @param sortHow Sort direction. Possible values: asc, desc.
     */
    @GET("users/{" + PATH_USERNAME + "}/lists/{" + PATH_ID + "}/items/{" + PATH_TYPE + "}/{" + PATH_SORT_BY + "}/{" + PATH_SORT_HOW + "}")
    Call<List<ListEntry>> listItems(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(PATH_ID) String id,
            @Path(PATH_TYPE) String type,
            @Path(PATH_SORT_BY) String sortBy,
            @Path(PATH_SORT_HOW) String sortHow,
            @Query(QUERY_PARAM_PAGE) int page,
            @Query(QUERY_PARAM_LIMIT) int limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Add one or more items to a custom list. Items can be movies, shows, seasons, episodes, or people.
     */
    @POST("users/{" + PATH_USERNAME + "}/lists/{" + PATH_ID + "}/items")
    Call<SyncResponse> addListItems(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(PATH_ID) String id,
            @Body SyncItems items
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Remove one or more items from a custom list.
     */
    @POST("users/{" + PATH_USERNAME + "}/lists/{" + PATH_ID + "}/items/remove")
    Call<SyncResponse> deleteListItems(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(PATH_ID) String id,
            @Body SyncItems items
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Reorder all items on a list by sending the updated rank of list item ids.
     */
    @POST("users/{" + PATH_USERNAME + "}/lists/{" + PATH_ID + "}/items/reorder")
    Call<ListReorderResponse> reorderListItems(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(PATH_ID) String id,
            @Body ListItemRank rank
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * If the user has a private profile, the follow request will require approval (approved_at will be null). If a user
     * is public, they will be followed immediately (approved_at will have a date).
     * <p>
     * Note: If this user is already being followed, a 409 HTTP status code will returned.
     */
    @POST("users/{" + PATH_USERNAME + "}/follow")
    Call<Followed> follow(
            @Path(PATH_USERNAME) UserSlug userSlug
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Unfollow someone you already follow.
     */
    @DELETE("users/{" + PATH_USERNAME + "}/follow")
    Call<Void> unfollow(
            @Path(PATH_USERNAME) UserSlug userSlug
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns all followers including when the relationship began.
     */
    @GET("users/{" + PATH_USERNAME + "}/followers")
    Call<List<Follower>> followers(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns all user's they follow including when the relationship began.
     */
    @GET("users/{" + PATH_USERNAME + "}/following")
    Call<List<Follower>> following(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns all friends for a user including when the relationship began. Friendship is a 2 way relationship where
     * each user follows the other.
     */
    @GET("users/{" + PATH_USERNAME + "}/friends")
    Call<List<Friend>> friends(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns movies and episodes that a user has watched, sorted by most recent.
     * <p>
     * The {@code id} uniquely identifies each history event and can be used to remove events individually using the
     * {@code POST /sync/history/remove method}. The action will be set to {@code scrobble}, {@code checkin}, or
     * {@code watch}.
     *
     * @param userSlug Example: "sean".
     * @see Sync#history(Integer, Integer, Extended, OffsetDateTime, OffsetDateTime)
     */
    @GET("users/{" + PATH_USERNAME + "}/history")
    Call<List<HistoryEntry>> history(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended,
            @Query(QUERY_PARAM_START_AT) OffsetDateTime startAt,
            @Query(QUERY_PARAM_END_AT) OffsetDateTime endAt
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Like {@link #history(UserSlug, Integer, Integer, Extended, OffsetDateTime, OffsetDateTime)}, but allows to set a
     * type to only return movies or episodes.
     *
     * @param userSlug Example: "sean".
     * @see Sync#history(HistoryType, Integer, Integer, Extended, OffsetDateTime, OffsetDateTime)
     */
    @GET("users/{" + PATH_USERNAME + "}/history/{" + PATH_TYPE + "}")
    Call<List<HistoryEntry>> history(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(PATH_TYPE) HistoryType type,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended,
            @Query(QUERY_PARAM_START_AT) OffsetDateTime startAt,
            @Query(QUERY_PARAM_END_AT) OffsetDateTime endAt
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
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
     * @see Sync#history(HistoryType, int, Integer, Integer, Extended, OffsetDateTime, OffsetDateTime)
     */
    @GET("users/{" + PATH_USERNAME + "}/history/{" + PATH_TYPE + "}/{" + PATH_ID + "}")
    Call<List<HistoryEntry>> history(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(PATH_TYPE) HistoryType type,
            @Path(PATH_ID) int id,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended,
            @Query(QUERY_PARAM_START_AT) OffsetDateTime startAt,
            @Query(QUERY_PARAM_END_AT) OffsetDateTime endAt
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Get a user's ratings filtered by movies. You can filter for a specific rating between 1 and 10.
     *
     * @param userSlug Example: "sean".
     * @param filter   Filter for a specific rating.
     */
    @GET("users/{" + PATH_USERNAME + "}/ratings/movies{" + PATH_RATING + "}")
    Call<List<RatedMovie>> ratingsMovies(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(value = PATH_RATING, encoded = true) RatingsFilter filter,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Get a user's ratings filtered by shows. You can filter for a specific rating between 1 and 10.
     *
     * @param userSlug Example: "sean".
     * @param filter   Filter for a specific rating.
     */
    @GET("users/{" + PATH_USERNAME + "}/ratings/shows{" + PATH_RATING + "}")
    Call<List<RatedShow>> ratingsShows(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(value = PATH_RATING, encoded = true) RatingsFilter filter,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Get a user's ratings filtered by seasons. You can filter for a specific rating between 1 and 10.
     *
     * @param userSlug Example: "sean".
     * @param filter   Filter for a specific rating.
     */
    @GET("users/{" + PATH_USERNAME + "}/ratings/seasons{" + PATH_RATING + "}")
    Call<List<RatedSeason>> ratingsSeasons(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(value = PATH_RATING, encoded = true) RatingsFilter filter,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Get a user's ratings filtered by episodes. You can filter for a specific rating between 1 and 10.
     *
     * @param userSlug Example: "sean".
     * @param filter   Filter for a specific rating.
     */
    @GET("users/{" + PATH_USERNAME + "}/ratings/episodes{" + PATH_RATING + "}")
    Call<List<RatedEpisode>> ratingsEpisodes(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Path(value = PATH_RATING, encoded = true) RatingsFilter filter,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * @deprecated Use {@link #watchlistMovies(UserSlug, Integer, Integer, Extended)} instead.
     */
    @Deprecated
    @GET("users/{" + PATH_USERNAME + "}/watchlist/movies")
    Call<List<BaseMovie>> watchlistMovies(
            @Nonnull @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns a page of items in a user's watchlist filtered by movies.
     * <p>
     * The watchlist should not be used as a list of what the user is actively watching. Use a combination of the
     * /sync/watched and /shows/:id/progress methods to get what the user is actively watching.
     * <p>
     * <b>Auto Removal</b>
     * <p>
     * When an item is watched, it will be automatically removed from the watchlist. For shows and seasons, watching 1
     * episode will remove the entire show or season.
     *
     * @see #watchlistMovies(UserSlug, String, String, Integer, Integer, Extended)
     */
    @GET("users/{" + PATH_USERNAME + "}/watchlist/movies")
    Call<List<BaseMovie>> watchlistMovies(
            @Nonnull @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * Like {@link #watchlistMovies(UserSlug, Integer, Integer, Extended)}, but you can specify a sort order.
     * <p>
     * The specified order will be sent in the X-Applied-Sort-By and X-Applied-Sort-How headers.
     * <p>
     * Some sort_by options are VIP Only including imdb_rating, tmdb_rating, rt_tomatometer, rt_audience, metascore,
     * votes, imdb_votes, and tmdb_votes. If sent for a non VIP, the items will fall back to rank.
     *
     * @param sortBy  Sort by a specific property. Possible values: rank, added, title, released , runtime, popularity,
     *                random, percentage, imdb_rating, tmdb_rating, rt_tomatometer, rt_audience, metascore, votes,
     *                imdb_votes, tmdb_votes, my_rating, watched, collected.
     * @param sortHow Sort direction. Possible values: asc, desc.
     */
    @GET("users/{" + PATH_USERNAME + "}/watchlist/movies/{" + PATH_SORT_BY + "}/{" + PATH_SORT_HOW + "}")
    Call<List<BaseMovie>> watchlistMovies(
            @Nonnull @Path(PATH_USERNAME) UserSlug userSlug,
            @Nonnull @Path(PATH_SORT_BY) String sortBy,
            @Nonnull @Path(PATH_SORT_HOW) String sortHow,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * @deprecated Use {@link #watchlistShows(UserSlug, Integer, Integer, Extended)} instead.
     */
    @Deprecated
    @GET("users/{" + PATH_USERNAME + "}/watchlist/shows")
    Call<List<BaseShow>> watchlistShows(
            @Nonnull @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns a page of items in a user's watchlist filtered by shows.
     * <p>
     * The watchlist should not be used as a list of what the user is actively watching. Use a combination of the
     * /sync/watched and /shows/:id/progress methods to get what the user is actively watching.
     * <p>
     * <b>Auto Removal</b>
     * <p>
     * When an item is watched, it will be automatically removed from the watchlist. For shows and seasons, watching 1
     * episode will remove the entire show or season.
     *
     * @see #watchlistShows(UserSlug, String, String, Integer, Integer, Extended)
     */
    @GET("users/{" + PATH_USERNAME + "}/watchlist/shows")
    Call<List<BaseShow>> watchlistShows(
            @Nonnull @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * Like {@link #watchlistShows(UserSlug, Integer, Integer, Extended)}, but you can specify a sort order.
     * <p>
     * The specified order will be sent in the X-Applied-Sort-By and X-Applied-Sort-How headers.
     * <p>
     * Some sort_by options are VIP Only including imdb_rating, tmdb_rating, rt_tomatometer, rt_audience, metascore,
     * votes, imdb_votes, and tmdb_votes. If sent for a non VIP, the items will fall back to rank.
     *
     * @param sortBy  Sort by a specific property. Possible values: rank, added, title, released , runtime, popularity,
     *                random, percentage, imdb_rating, tmdb_rating, rt_tomatometer, rt_audience, metascore, votes,
     *                imdb_votes, tmdb_votes, my_rating, watched, collected.
     * @param sortHow Sort direction. Possible values: asc, desc.
     */
    @GET("users/{" + PATH_USERNAME + "}/watchlist/shows/{" + PATH_SORT_BY + "}/{" + PATH_SORT_HOW + "}")
    Call<List<BaseShow>> watchlistShows(
            @Nonnull @Path(PATH_USERNAME) UserSlug userSlug,
            @Nonnull @Path(PATH_SORT_BY) String sortBy,
            @Nonnull @Path(PATH_SORT_HOW) String sortHow,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * @deprecated Use {@link #watchlistSeasons(UserSlug, Integer, Integer, Extended)} instead.
     */
    @Deprecated
    @GET("users/{" + PATH_USERNAME + "}/watchlist/seasons")
    Call<List<WatchlistedSeason>> watchlistSeasons(
            @Nonnull @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns a page of items in a user's watchlist filtered by seasons.
     * <p>
     * The watchlist should not be used as a list of what the user is actively watching. Use a combination of the
     * /sync/watched and /shows/:id/progress methods to get what the user is actively watching.
     * <p>
     * <b>Auto Removal</b>
     * <p>
     * When an item is watched, it will be automatically removed from the watchlist. For shows and seasons, watching 1
     * episode will remove the entire show or season.
     *
     * @see #watchlistSeasons(UserSlug, String, String, Integer, Integer, Extended)
     */
    @GET("users/{" + PATH_USERNAME + "}/watchlist/seasons")
    Call<List<WatchlistedSeason>> watchlistSeasons(
            @Nonnull @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * Like {@link #watchlistSeasons(UserSlug, Integer, Integer, Extended)}, but you can specify a sort order.
     * <p>
     * The specified order will be sent in the X-Applied-Sort-By and X-Applied-Sort-How headers.
     * <p>
     * Some sort_by options are VIP Only including imdb_rating, tmdb_rating, rt_tomatometer, rt_audience, metascore,
     * votes, imdb_votes, and tmdb_votes. If sent for a non VIP, the items will fall back to rank.
     *
     * @param sortBy  Sort by a specific property. Possible values: rank, added, title, released , runtime, popularity,
     *                random, percentage, imdb_rating, tmdb_rating, rt_tomatometer, rt_audience, metascore, votes,
     *                imdb_votes, tmdb_votes, my_rating, watched, collected.
     * @param sortHow Sort direction. Possible values: asc, desc.
     */
    @GET("users/{" + PATH_USERNAME + "}/watchlist/seasons/{" + PATH_SORT_BY + "}/{" + PATH_SORT_HOW + "}")
    Call<List<WatchlistedSeason>> watchlistSeasons(
            @Nonnull @Path(PATH_USERNAME) UserSlug userSlug,
            @Nonnull @Path(PATH_SORT_BY) String sortBy,
            @Nonnull @Path(PATH_SORT_HOW) String sortHow,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * @deprecated Use {@link #watchlistEpisodes(UserSlug, Integer, Integer, Extended)} instead.
     */
    @Deprecated
    @GET("users/{" + PATH_USERNAME + "}/watchlist/episodes")
    Call<List<WatchlistedEpisode>> watchlistEpisodes(
            @Nonnull @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns a page of items in a user's watchlist filtered by episodes.
     * <p>
     * The watchlist should not be used as a list of what the user is actively watching. Use a combination of the
     * /sync/watched and /shows/:id/progress methods to get what the user is actively watching.
     * <p>
     * <b>Auto Removal</b>
     * <p>
     * When an item is watched, it will be automatically removed from the watchlist. For shows and seasons, watching 1
     * episode will remove the entire show or season.
     *
     * @see #watchlistEpisodes(UserSlug, String, String, Integer, Integer, Extended)
     */
    @GET("users/{" + PATH_USERNAME + "}/watchlist/episodes")
    Call<List<WatchlistedEpisode>> watchlistEpisodes(
            @Nonnull @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * Like {@link #watchlistEpisodes(UserSlug, Integer, Integer, Extended)}, but you can specify a sort order.
     * <p>
     * The specified order will be sent in the X-Applied-Sort-By and X-Applied-Sort-How headers.
     * <p>
     * Some sort_by options are VIP Only including imdb_rating, tmdb_rating, rt_tomatometer, rt_audience, metascore,
     * votes, imdb_votes, and tmdb_votes. If sent for a non VIP, the items will fall back to rank.
     *
     * @param sortBy  Sort by a specific property. Possible values: rank, added, title, released , runtime, popularity,
     *                random, percentage, imdb_rating, tmdb_rating, rt_tomatometer, rt_audience, metascore, votes,
     *                imdb_votes, tmdb_votes, my_rating, watched, collected.
     * @param sortHow Sort direction. Possible values: asc, desc.
     */
    @GET("users/{" + PATH_USERNAME + "}/watchlist/episodes/{" + PATH_SORT_BY + "}/{" + PATH_SORT_HOW + "}")
    Call<List<WatchlistedEpisode>> watchlistEpisodes(
            @Nonnull @Path(PATH_USERNAME) UserSlug userSlug,
            @Nonnull @Path(PATH_SORT_BY) String sortBy,
            @Nonnull @Path(PATH_SORT_HOW) String sortHow,
            @Query(QUERY_PARAM_PAGE) Integer page,
            @Query(QUERY_PARAM_LIMIT) Integer limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns all movies a user has watched sorted by most plays.
     *
     * @param userSlug Example: "sean".
     * @deprecated Use {@link #watchedMovies(UserSlug, int, int, ExtendedMoviesWatched)} instead.
     */
    @Deprecated
    @GET("users/{" + PATH_USERNAME + "}/watched/movies")
    Call<List<BaseMovie>> watchedMovies(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns all movies a user has watched sorted by most plays.
     *
     * @param userSlug Example: "sean".
     * @param page     Number of page of results to be returned.
     * @param limit    Number of results to return per page.
     */
    @GET("users/{" + PATH_USERNAME + "}/watched/movies")
    Call<List<BaseMovie>> watchedMovies(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(QUERY_PARAM_PAGE) int page,
            @Query(QUERY_PARAM_LIMIT) int limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) ExtendedMoviesWatched extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns movie Trakt IDs mapped to a list of watched at timestamps.
     * <p>
     * An example for an equivalent JSON for a single movie with three watched at timestamps:
     * <pre>
     * {
     *   "443": [
     *     "2009-08-09T13:37:00.000Z",
     *     "2012-07-14T08:07:00.000Z",
     *     "2013-06-03T08:26:00.000Z"
     *   ]
     * }
     * </pre>
     */
    @GET("users/{" + PATH_USERNAME + "}/watched/movies?" + QUERY_PARAM_EXTENDED_MIN)
    Call<Map<String, List<OffsetDateTime>>> watchedMoviesMin(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(QUERY_PARAM_PAGE) int page,
            @Query(QUERY_PARAM_LIMIT) int limit
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns all shows a user has watched sorted by most plays.
     *
     * @param userSlug Example: "sean".
     * @deprecated Use {@link #watchedShows(UserSlug, int, int, ExtendedShowsWatched)} instead.
     */
    @Deprecated
    @GET("users/{" + PATH_USERNAME + "}/watched/shows")
    Call<List<BaseShow>> watchedShows(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) Extended extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns all shows a user has watched sorted by most plays.
     *
     * @param userSlug Example: "sean".
     * @param page     Number of page of results to be returned.
     * @param limit    Number of results to return per page.
     * @deprecated Use {@link #watchedShows(UserSlug, int, int, ExtendedShowsWatched, Specials)} instead.
     */
    @Deprecated
    @GET("users/{" + PATH_USERNAME + "}/watched/shows")
    Call<List<BaseShow>> watchedShows(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(QUERY_PARAM_PAGE) int page,
            @Query(QUERY_PARAM_LIMIT) int limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) ExtendedShowsWatched extended
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns all shows a user has watched sorted by most plays.
     *
     * @param userSlug Example: "sean".
     * @param page     Number of page of results to be returned.
     * @param limit    Number of results to return per page.
     */
    @GET("users/{" + PATH_USERNAME + "}/watched/shows")
    Call<List<BaseShow>> watchedShows(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(QUERY_PARAM_PAGE) int page,
            @Query(QUERY_PARAM_LIMIT) int limit,
            @Query(value = QUERY_PARAM_EXTENDED, encoded = true) ExtendedShowsWatched extended,
            @Query(value = QUERY_PARAM_SPECIALS, encoded = true) Specials specials
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns show Trakt IDs mapped to season Trakt IDs, mapped to episode Trakt IDs mapped to a list of watched at
     * timestamps.
     * <p>
     * An example for an equivalent JSON for a single show, season and episode with three watched at timestamps:
     * <pre>
     * {
     *   "77712": {
     *     "95726": {
     *       "1498291": [
     *         "2026-04-29T17:54:00.000Z",
     *         "2026-04-29T17:57:00.000Z",
     *         "2026-05-08T02:38:00.000Z"
     *       ]
     *     }
     *   }
     * }
     * </pre>
     *
     * @deprecated Use {@link #watchedShowsMin(UserSlug, int, int, Specials)} instead.
     */
    @Deprecated
    @GET("users/{" + PATH_USERNAME + "}/watched/shows?" + QUERY_PARAM_EXTENDED_MIN)
    Call<Map<String, Map<String, Map<String, List<OffsetDateTime>>>>> watchedShowsMin(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(QUERY_PARAM_PAGE) int page,
            @Query(QUERY_PARAM_LIMIT) int limit
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} optional</b>
     * <p>
     * Returns show Trakt IDs mapped to season Trakt IDs, mapped to episode Trakt IDs mapped to a list of watched at
     * timestamps.
     * <p>
     * An example for an equivalent JSON for a single show, season and episode with three watched at timestamps:
     * <pre>
     * {
     *   "77712": {
     *     "95726": {
     *       "1498291": [
     *         "2026-04-29T17:54:00.000Z",
     *         "2026-04-29T17:57:00.000Z",
     *         "2026-05-08T02:38:00.000Z"
     *       ]
     *     }
     *   }
     * }
     * </pre>
     */
    @GET("users/{" + PATH_USERNAME + "}/watched/shows?" + QUERY_PARAM_EXTENDED_MIN)
    Call<Map<String, Map<String, Map<String, List<OffsetDateTime>>>>> watchedShowsMin(
            @Path(PATH_USERNAME) UserSlug userSlug,
            @Query(QUERY_PARAM_PAGE) int page,
            @Query(QUERY_PARAM_LIMIT) int limit,
            @Query(value = QUERY_PARAM_SPECIALS, encoded = true) Specials specials
    );

}
