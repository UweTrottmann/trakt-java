package com.redissi.trakt.services

import com.redissi.trakt.entities.*
import com.redissi.trakt.enums.Extended
import com.redissi.trakt.enums.RatingsFilter
import retrofit2.http.*

interface Sync {
    /**
     * **OAuth Required**
     *
     *
     *  This method is a useful first step in the syncing process. We recommended caching these dates locally, then
     * you can compare to know exactly what data has changed recently. This can greatly optimize your syncs so you don't
     * pull down a ton of data only to see nothing has actually changed.
     */
    @GET("sync/last_activities")
    suspend fun lastActivities(): LastActivities?

    /**
     * **OAuth Required**
     *
     *
     *  Get all collected movies in a user's collection. A collected item indicates availability to watch digitally
     * or on physical media.
     */
    @GET("sync/collection/movies")
    suspend fun collectionMovies(
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<BaseMovie>?

    /**
     * **OAuth Required**
     *
     *
     *  Get all collected shows in a user's collection. A collected item indicates availability to watch digitally or
     * on physical media.
     */
    @GET("sync/collection/shows")
    suspend fun collectionShows(
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<BaseShow>?

    /**
     * **OAuth Required**
     *
     *
     *  Add one or more items to a user's collection including the format of the item.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/collection")
    suspend fun addItemsToCollection(
        @Body items: SyncItems?
    ): SyncResponse?

    /**
     * **OAuth Required**
     *
     *
     *  Remove one or more items from a user's collection.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/collection/remove")
    suspend fun deleteItemsFromCollection(
        @Body items: SyncItems?
    ): SyncResponse?

    /**
     * **OAuth Required**
     *
     *
     *  Returns all movies a user has watched.
     */
    @GET("sync/watched/movies")
    suspend fun watchedMovies(
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<BaseMovie>?

    /**
     * **OAuth Required**
     *
     *
     *  Returns all playbacks;
     */
    @GET("sync/playback")
    suspend fun getPlayback(
        @Query("limit") limit: Int?
    ): List<PlaybackResponse>?

    /**
     * **OAuth Required**
     *
     *
     *  Returns all playbacks;
     */
    @GET("sync/playback/episodes")
    suspend fun getPlaybackEpisodes(
        @Query("limit") limit: Int?
    ): List<PlaybackResponse>?

    /**
     * **OAuth Required**
     *
     *
     *  Returns all playbacks;
     */
    @GET("sync/playback/movies")
    suspend fun getPlaybackMovies(
        @Query("limit") limit: Int?
    ): List<PlaybackResponse>?

    /**
     * **OAuth Required**
     *
     *
     *  Returns all shows a user has watched.
     */
    @GET("sync/watched/shows")
    suspend fun watchedShows(
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<BaseShow>?

    /**
     * **OAuth Required**
     *
     *
     *  Add items to a user's watch history. Accepts shows, seasons, episodes and movies. If only a show is passed,
     * assumes all seasons are to be marked watched. Same for seasons. Send a `watched_at` UTC datetime to
     * mark items as watched in the past. This is useful for syncing past watches from a media center.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/history")
    suspend fun addItemsToWatchedHistory(
        @Body items: SyncItems?
    ): SyncResponse?

    /**
     * **OAuth Required**
     *
     *
     *  Remove items from a user's watch history including all watches, scrobbles, and checkins. Accepts shows,
     * seasons, episodes and movies. If only a show is passed, assumes all seasons are to be removed from history. Same
     * for seasons.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/history/remove")
    suspend fun deleteItemsFromWatchedHistory(
        @Body items: SyncItems?
    ): SyncResponse?

    /**
     * **OAuth Required**
     *
     *
     *  Get a user's ratings filtered by movies. You can filter for a specific rating between 1 and 10.
     *
     * @param filter Filter for a specific rating.
     */
    @GET("sync/ratings/movies{rating}")
    suspend fun ratingsMovies(
        @Path(value = "rating", encoded = true) filter: RatingsFilter?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<RatedMovie>?

    /**
     * **OAuth Required**
     *
     *
     *  Get a user's ratings filtered by shows. You can filter for a specific rating between 1 and 10.
     *
     * @param filter Filter for a specific rating.
     */
    @GET("sync/ratings/shows{rating}")
    suspend fun ratingsShows(
        @Path(value = "rating", encoded = true) filter: RatingsFilter?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<RatedShow>?

    /**
     * **OAuth Required**
     *
     *
     *  Get a user's ratings filtered by seasons. You can filter for a specific rating between 1 and 10.
     *
     * @param filter Filter for a specific rating.
     */
    @GET("sync/ratings/seasons{rating}")
    suspend fun ratingsSeasons(
        @Path(value = "rating", encoded = true) filter: RatingsFilter?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<RatedSeason>?

    /**
     * **OAuth Required**
     *
     *
     *  Get a user's ratings filtered by episodes. You can filter for a specific rating between 1 and 10.
     *
     * @param filter Filter for a specific rating.
     */
    @GET("sync/ratings/episodes{rating}")
    suspend fun ratingsEpisodes(
        @Path(value = "rating", encoded = true) filter: RatingsFilter?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<RatedEpisode>?

    /**
     * **OAuth Required**
     *
     *
     *  Rate one or more items.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/ratings")
    suspend fun addRatings(
        @Body items: SyncItems?
    ): SyncResponse?

    /**
     * **OAuth Required**
     *
     *
     *  Delete ratings for one or more items.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/ratings/remove")
    suspend fun deleteRatings(
        @Body items: SyncItems?
    ): SyncResponse?

    /**
     * **OAuth Required**
     *
     *
     *  Returns all items in a user's watchlist filtered by movies. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("sync/watchlist/movies")
    suspend fun watchlistMovies(
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<BaseMovie>?

    /**
     * **OAuth Required**
     *
     *
     *  Returns all items in a user's watchlist filtered by shows. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("sync/watchlist/shows")
    suspend fun watchlistShows(
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<BaseShow>?

    /**
     * **OAuth Required**
     *
     *
     *  Returns all items in a user's watchlist filtered by seasons. When an item is watched, it will be
     * automatically removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("sync/watchlist/seasons")
    suspend fun watchlistSeasons(
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<WatchlistedSeason>?

    /**
     * **OAuth Required**
     *
     *
     *  Returns all items in a user's watchlist filtered by episodes. When an item is watched, it will be
     * automatically removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("sync/watchlist/episodes")
    suspend fun watchlistEpisodes(
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<WatchlistedEpisode>?

    /**
     * **OAuth Required**
     *
     *
     *  Add one of more items to a user's watchlist.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/watchlist")
    suspend fun addItemsToWatchlist(
        @Body items: SyncItems?
    ): SyncResponse?

    /**
     * **OAuth Required**
     *
     *
     *  Delete one or more items from a user's watchlist.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("sync/watchlist/remove")
    suspend fun deleteItemsFromWatchlist(
        @Body items: SyncItems?
    ): SyncResponse?
}