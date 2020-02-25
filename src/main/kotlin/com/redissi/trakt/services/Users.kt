package com.redissi.trakt.services

import com.redissi.trakt.entities.*
import com.redissi.trakt.enums.Extended
import com.redissi.trakt.enums.HistoryType
import com.redissi.trakt.enums.RatingsFilter
import retrofit2.Response
import retrofit2.http.*
import java.time.OffsetDateTime

interface Users {
    /**
     * **OAuth Required**
     *
     *
     *  Get the user's settings so you can align your app's experience with what they're used to on the trakt
     * website.
     */
    @GET("users/settings")
    suspend fun settings(): Settings?

    /**
     * **OAuth Optional**
     *
     *
     *  Get a user's profile information. If the user is private, info will only be returned if you send OAuth and
     * are either that user or an approved follower.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}")
    suspend fun profile(
        @Path("username") userSlug: UserSlug?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): User?

    /**
     * **OAuth Optional**
     *
     *
     *  Get all collected movies in a user's collection. A collected item indicates availability to watch digitally
     * or on physical media.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}/collection/movies")
    suspend fun collectionMovies(
        @Path("username") userSlug: UserSlug?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<BaseMovie>?

    /**
     * **OAuth Optional**
     *
     *
     *  Get all collected shows in a user's collection. A collected item indicates availability to watch digitally or
     * on physical media.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}/collection/shows")
    suspend fun collectionShows(
        @Path("username") userSlug: UserSlug?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<BaseShow>?

    /**
     * **OAuth Optional**
     *
     *
     *  Returns all custom lists for a user.
     */
    @GET("users/{username}/lists")
    suspend fun lists(
        @Path("username") userSlug: UserSlug?
    ): List<TraktList>?

    /**
     * **OAuth Required**
     *
     *
     *  Create a new custom list. The name is the only required field, but the other info is recommended to ask for.
     */
    @POST("users/{username}/lists")
    suspend fun createList(
        @Path("username") userSlug: UserSlug?,
        @Body list: TraktList?
    ): TraktList?

    /**
     * **OAuth Required**
     *
     *
     *  Update a custom list by sending 1 or more parameters. If you update the list name, the original slug will
     * still be retained so existing references to this list won't break.
     */
    @PUT("users/{username}/lists/{id}")
    suspend fun updateList(
        @Path("username") userSlug: UserSlug?,
        @Path("id") id: String?,
        @Body list: TraktList?
    ): TraktList?

    /**
     * **OAuth Required**
     *
     *
     *  Remove a custom list and all items it contains.
     */
    @DELETE("users/{username}/lists/{id}")
    suspend fun deleteList(
        @Path("username") userSlug: UserSlug?,
        @Path("id") id: String?
    ): Response<Unit>

    /**
     * **OAuth Optional**
     *
     *
     *  Get all items on a custom list. Items can be movies, shows, seasons, episodes, or people.
     */
    @GET("users/{username}/lists/{id}/items")
    suspend fun listItems(
        @Path("username") userSlug: UserSlug?,
        @Path("id") id: String?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<ListEntry>?

    /**
     * **OAuth Required**
     *
     *
     *  Add one or more items to a custom list. Items can be movies, shows, seasons, episodes, or people.
     */
    @POST("users/{username}/lists/{id}/items")
    suspend fun addListItems(
        @Path("username") userSlug: UserSlug?,
        @Path("id") id: String?,
        @Body items: SyncItems?
    ): SyncResponse?

    /**
     * **OAuth Required**
     *
     *
     *  Remove one or more items from a custom list.
     */
    @POST("users/{username}/lists/{id}/items/remove")
    suspend fun deleteListItems(
        @Path("username") userSlug: UserSlug?,
        @Path("id") id: String?,
        @Body items: SyncItems?
    ): SyncResponse?

    /**
     * **OAuth Required**
     *
     *
     *  Reorder all items on a list by sending the updated rank of list item ids.
     */
    @POST("users/{username}/lists/{id}/items/reorder")
    suspend fun reorderListItems(
        @Path("username") userSlug: UserSlug?,
        @Path("id") id: String?,
        @Body rank: ListItemRank?
    ): ListReorderResponse?

    /**
     * **OAuth Required**
     *
     *
     * If the user has a private profile, the follow request will require approval (approved_at will be null). If a
     * user is public, they will be followed immediately (approved_at will have a date).
     *
     *
     * Note: If this user is already being followed, a 409 HTTP status code will returned.
     */
    @POST("users/{username}/follow")
    suspend fun follow(
        @Path("username") userSlug: UserSlug?
    ): Followed?

    /**
     * **OAuth Required**
     *
     *
     * Unfollow someone you already follow.
     */
    @DELETE("users/{username}/follow")
    suspend fun unfollow(
        @Path("username") userSlug: UserSlug?
    ): Response<Unit>

    /**
     * **OAuth Optional**
     *
     *
     * Returns all followers including when the relationship began.
     */
    @GET("users/{username}/followers")
    suspend fun followers(
        @Path("username") userSlug: UserSlug?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<Follower>?

    /**
     * **OAuth Optional**
     *
     *
     * Returns all user's they follow including when the relationship began.
     */
    @GET("users/{username}/following")
    suspend fun following(
        @Path("username") userSlug: UserSlug?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<Follower>?

    /**
     * **OAuth Optional**
     *
     *
     * Returns all friends for a user including when the relationship began. Friendship is a 2 way relationship where
     * each user follows the other.
     */
    @GET("users/{username}/friends")
    suspend fun friends(
        @Path("username") userSlug: UserSlug?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<Friend>?

    /**
     * **OAuth Optional**
     *
     *
     * Returns movies and episodes that a user has watched, sorted by most recent.
     *
     *
     * The `id` uniquely identifies each history event and can be used to remove events individually using the
     * `POST /sync/history/remove method`. The action will be set to `scrobble`, `checkin`, or `watch`.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}/history")
    suspend fun history(
        @Path("username") userSlug: UserSlug?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
        @Query(value = "extended", encoded = true) extended: Extended?,
        @Query("start_at") startAt: OffsetDateTime?,
        @Query("end_at") endAt: OffsetDateTime?
    ): List<HistoryEntry>?

    /**
     * **OAuth Optional**
     *
     *
     * Returns movies or episodes that a user has watched, sorted by most recent.
     *
     *
     * The `id` uniquely identifies each history event and can be used to remove events individually using the
     * `POST /sync/history/remove method`. The action will be set to `scrobble`, `checkin`, or `watch`.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}/history/{type}")
    suspend fun history(
        @Path("username") userSlug: UserSlug?,
        @Path("type") type: HistoryType?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
        @Query(value = "extended", encoded = true) extended: Extended?,
        @Query("start_at") startAt: OffsetDateTime?,
        @Query("end_at") endAt: OffsetDateTime?
    ): List<HistoryEntry>?

    /**
     * **OAuth Optional**
     *
     *
     * Returns the history for just the specified item. For example, `/history/movies/12601` would return all
     * watches for TRON: Legacy and `/history/shows/1388` would return all watched episodes for Breaking Bad. If
     * an invalid `id` is sent, a 404 error will be returned. If the `id` is valid, but there is no history,
     * an empty array will be returned.
     *
     *
     * The `id` uniquely identifies each history event and can be used to remove events individually using the
     * `POST /sync/history/remove method`. The action will be set to `scrobble`, `checkin`, or `watch`.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}/history/{type}/{id}")
    suspend fun history(
        @Path("username") userSlug: UserSlug?,
        @Path("type") type: HistoryType?,
        @Path("id") id: Int,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
        @Query(value = "extended", encoded = true) extended: Extended?,
        @Query("start_at") startAt: OffsetDateTime?,
        @Query("end_at") endAt: OffsetDateTime?
    ): List<HistoryEntry>?

    /**
     * **OAuth Optional**
     *
     *
     *  Get a user's ratings filtered by movies. You can filter for a specific rating between 1 and 10.
     *
     * @param userSlug Example: "sean".
     * @param filter Filter for a specific rating.
     */
    @GET("users/{username}/ratings/movies{rating}")
    suspend fun ratingsMovies(
        @Path("username") userSlug: UserSlug?,
        @Path(value = "rating", encoded = true) filter: RatingsFilter?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<RatedMovie>?

    /**
     * **OAuth Optional**
     *
     *
     *  Get a user's ratings filtered by shows. You can filter for a specific rating between 1 and 10.
     *
     * @param userSlug Example: "sean".
     * @param filter Filter for a specific rating.
     */
    @GET("users/{username}/ratings/shows{rating}")
    suspend fun ratingsShows(
        @Path("username") userSlug: UserSlug?,
        @Path(value = "rating", encoded = true) filter: RatingsFilter?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<RatedShow>?

    /**
     * **OAuth Optional**
     *
     *
     *  Get a user's ratings filtered by seasons. You can filter for a specific rating between 1 and 10.
     *
     * @param userSlug Example: "sean".
     * @param filter Filter for a specific rating.
     */
    @GET("users/{username}/ratings/seasons{rating}")
    suspend fun ratingsSeasons(
        @Path("username") userSlug: UserSlug?,
        @Path(value = "rating", encoded = true) filter: RatingsFilter?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<RatedSeason>?

    /**
     * **OAuth Optional**
     *
     *
     *  Get a user's ratings filtered by episodes. You can filter for a specific rating between 1 and 10.
     *
     * @param userSlug Example: "sean".
     * @param filter Filter for a specific rating.
     */
    @GET("users/{username}/ratings/episodes{rating}")
    suspend fun ratingsEpisodes(
        @Path("username") userSlug: UserSlug?,
        @Path(value = "rating", encoded = true) filter: RatingsFilter?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<RatedEpisode>?

    /**
     * **OAuth Optional**
     *
     *
     * Returns all items in a user's watchlist filtered by movies. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("users/{username}/watchlist/movies")
    suspend fun watchlistMovies(
        @Path("username") userSlug: UserSlug?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<BaseMovie>?

    /**
     * **OAuth Optional**
     *
     *
     * Returns all items in a user's watchlist filtered by shows. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("users/{username}/watchlist/shows")
    suspend fun watchlistShows(
        @Path("username") userSlug: UserSlug?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<BaseShow>?

    /**
     * **OAuth Optional**
     *
     *
     * Returns all items in a user's watchlist filtered by seasons. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("users/{username}/watchlist/seasons")
    suspend fun watchlistSeasons(
        @Path("username") userSlug: UserSlug?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<WatchlistedSeason>?

    /**
     * **OAuth Optional**
     *
     *
     * Returns all items in a user's watchlist filtered by episodes. When an item is watched, it will be
     * automatically removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("users/{username}/watchlist/episodes")
    suspend fun watchlistEpisodes(
        @Path("username") userSlug: UserSlug?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<WatchlistedEpisode>?

    /**
     * **OAuth Optional**
     *
     *
     *  Returns all movies or shows a user has watched sorted by most plays.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}/watched/movies")
    suspend fun watchedMovies(
        @Path("username") userSlug: UserSlug?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<BaseMovie>?

    /**
     * **OAuth Optional**
     *
     *
     *  Returns all movies or shows a user has watched sorted by most plays.
     *
     * @param userSlug Example: "sean".
     */
    @GET("users/{username}/watched/shows")
    suspend fun watchedShows(
        @Path("username") userSlug: UserSlug?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<BaseShow>?
}