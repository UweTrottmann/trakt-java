package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.BaseMovie;
import com.uwetrottmann.trakt.v2.entities.BaseShow;
import com.uwetrottmann.trakt.v2.entities.LastActivities;
import com.uwetrottmann.trakt.v2.entities.RatedEpisode;
import com.uwetrottmann.trakt.v2.entities.RatedMovie;
import com.uwetrottmann.trakt.v2.entities.RatedSeason;
import com.uwetrottmann.trakt.v2.entities.RatedShow;
import com.uwetrottmann.trakt.v2.entities.SyncItems;
import com.uwetrottmann.trakt.v2.entities.SyncRatedItems;
import com.uwetrottmann.trakt.v2.entities.SyncResponse;
import com.uwetrottmann.trakt.v2.entities.WatchlistedEpisode;
import com.uwetrottmann.trakt.v2.enums.Extended;
import com.uwetrottmann.trakt.v2.enums.RatingsFilter;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import retrofit.http.Body;
import retrofit.http.EncodedPath;
import retrofit.http.EncodedQuery;
import retrofit.http.GET;
import retrofit.http.POST;

import java.util.List;

public interface Sync {

    /**
     * <b>OAuth Required</b>
     *
     * <p> This method is a useful first step in the syncing process. We recommended caching these dates locally, then
     * you can compare to know exactly what data has changed recently. This can greatly optimize your syncs so you don't
     * pull down a ton of data only to see nothing has actually changed.
     */
    @GET("/sync/last_activities")
    LastActivities lastActivities() throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Get all collected movies in a user's collection. A collected item indicates availability to watch digitally
     * or on physical media.
     */
    @GET("/sync/collection/movies")
    List<BaseMovie> collectionMovies(
            @EncodedQuery("extended") Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Get all collected shows in a user's collection. A collected item indicates availability to watch digitally or
     * on physical media.
     */
    @GET("/sync/collection/shows")
    List<BaseShow> collectionShows(
            @EncodedQuery("extended") Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Add one or more items to a user's collection including the format of the item.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("/sync/collection")
    SyncResponse addItemsToCollection(
            @Body SyncItems items
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Remove one or more items from a user's collection.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("/sync/collection/remove")
    SyncResponse deleteItemsFromCollection(
            @Body SyncItems items
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all movies a user has watched.
     */
    @GET("/sync/watched/movies")
    List<BaseMovie> watchedMovies(
            @EncodedQuery("extended") Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all shows a user has watched.
     */
    @GET("/sync/watched/shows")
    List<BaseShow> watchedShows(
            @EncodedQuery("extended") Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Add items to a user's watch history. Accepts shows, seasons, episodes and movies. If only a show is passed,
     * assumes all seasons are to be marked watched. Same for seasons. Send a <code>watched_at</code> UTC datetime to
     * mark items as watched in the past. This is useful for syncing past watches from a media center.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("/sync/history")
    SyncResponse addItemsToWatchedHistory(
            @Body SyncItems items
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Remove items from a user's watch history including all watches, scrobbles, and checkins. Accepts shows,
     * seasons, episodes and movies. If only a show is passed, assumes all seasons are to be removed from history. Same
     * for seasons.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("/sync/history/remove")
    SyncResponse deleteItemsFromWatchedHistory(
            @Body SyncItems items
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Get a user's ratings filtered by movies. You can filter for a specific rating between 1 and 10.
     *
     * @param filter Filter for a specific rating.
     */
    @GET("/sync/ratings/movies{rating}")
    List<RatedMovie> ratingsMovies(
            @EncodedPath("rating") RatingsFilter filter,
            @EncodedQuery("extended") Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Get a user's ratings filtered by shows. You can filter for a specific rating between 1 and 10.
     *
     * @param filter Filter for a specific rating.
     */
    @GET("/sync/ratings/shows{rating}")
    List<RatedShow> ratingsShows(
            @EncodedPath("rating") RatingsFilter filter,
            @EncodedQuery("extended") Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Get a user's ratings filtered by seasons. You can filter for a specific rating between 1 and 10.
     *
     * @param filter Filter for a specific rating.
     */
    @GET("/sync/ratings/seasons{rating}")
    List<RatedSeason> ratingsSeasons(
            @EncodedPath("rating") RatingsFilter filter,
            @EncodedQuery("extended") Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Get a user's ratings filtered by episodes. You can filter for a specific rating between 1 and 10.
     *
     * @param filter Filter for a specific rating.
     */
    @GET("/sync/ratings/episodes{rating}")
    List<RatedEpisode> ratingsEpisodes(
            @EncodedPath("rating") RatingsFilter filter,
            @EncodedQuery("extended") Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Rate one or more items.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("/sync/ratings")
    SyncResponse addRatings(
            @Body SyncRatedItems items
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Delete ratings for one or more items.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("/sync/ratings/remove")
    SyncResponse deleteRatings(
            @Body SyncItems items
    ) throws OAuthUnauthorizedException;


    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all items in a user's watchlist filtered by movies. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("/sync/watchlist/movies")
    List<BaseMovie> watchlistMovies(
            @EncodedQuery("extended") Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all items in a user's watchlist filtered by shows. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("/sync/watchlist/shows")
    List<BaseShow> watchlistShows(
            @EncodedQuery("extended") Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all items in a user's watchlist filtered by episodes. When an item is watched, it will be
     * automatically removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("/sync/watchlist/episodes")
    List<WatchlistedEpisode> watchlistEpisodes(
            @EncodedQuery("extended") Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Add one of more items to a user's watchlist.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("/sync/watchlist")
    SyncResponse addItemsToWatchlist(
            @Body SyncItems items
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Delete one or more items from a user's watchlist.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("/sync/watchlist/remove")
    SyncResponse deleteItemsFromWatchlist(
            @Body SyncItems items
    ) throws OAuthUnauthorizedException;

}
