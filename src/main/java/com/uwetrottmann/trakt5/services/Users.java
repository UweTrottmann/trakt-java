package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.entities.BaseMovie;
import com.uwetrottmann.trakt5.entities.BaseShow;
import com.uwetrottmann.trakt5.entities.Followed;
import com.uwetrottmann.trakt5.entities.Follower;
import com.uwetrottmann.trakt5.entities.Friend;
import com.uwetrottmann.trakt5.entities.HistoryEntry;
import com.uwetrottmann.trakt5.entities.ListEntry;
import com.uwetrottmann.trakt5.entities.RatedEpisode;
import com.uwetrottmann.trakt5.entities.RatedMovie;
import com.uwetrottmann.trakt5.entities.RatedSeason;
import com.uwetrottmann.trakt5.entities.RatedShow;
import com.uwetrottmann.trakt5.entities.Settings;
import com.uwetrottmann.trakt5.entities.SyncItems;
import com.uwetrottmann.trakt5.entities.SyncResponse;
import com.uwetrottmann.trakt5.entities.User;
import com.uwetrottmann.trakt5.entities.Username;
import com.uwetrottmann.trakt5.entities.WatchlistedEpisode;
import com.uwetrottmann.trakt5.entities.WatchlistedSeason;
import com.uwetrottmann.trakt5.enums.Extended;
import com.uwetrottmann.trakt5.enums.HistoryType;
import com.uwetrottmann.trakt5.enums.RatingsFilter;
import com.uwetrottmann.trakt5.exceptions.OAuthUnauthorizedException;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

public interface Users {

    /**
     * <b>OAuth Required</b>
     *
     * <p> Get the user's settings so you can align your app's experience with what they're used to on the trakt
     * website.
     */
    @GET("/users/settings")
    Settings settings() throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Get a user's profile information. If the user is private, info will only be returned if you send OAuth and
     * are either that user or an approved follower.
     *
     * @param username Example: "sean".
     */
    @GET("/users/{username}")
    User profile(
            @Path("username") Username username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Get all collected movies in a user's collection. A collected item indicates availability to watch digitally
     * or on physical media.
     *
     * @param username Example: "sean".
     */
    @GET("/users/{username}/collection/movies")
    List<BaseMovie> collectionMovies(
            @Path("username") Username username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Get all collected shows in a user's collection. A collected item indicates availability to watch digitally or
     * on physical media.
     *
     * @param username Example: "sean".
     */
    @GET("/users/{username}/collection/shows")
    List<BaseShow> collectionShows(
            @Path("username") Username username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Returns all custom lists for a user.
     */
    @GET("/users/{username}/lists")
    List<com.uwetrottmann.trakt5.entities.List> lists(
            @Path("username") Username username
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Create a new custom list. The name is the only required field, but the other info is recommended to ask for.
     */
    @POST("/users/{username}/lists")
    com.uwetrottmann.trakt5.entities.List createList(
            @Path("username") Username username,
            @Body com.uwetrottmann.trakt5.entities.List list
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Update a custom list by sending 1 or more parameters. If you update the list name, the original slug will
     * still be retained so existing references to this list won't break.
     */
    @PUT("/users/{username}/lists/{id}")
    com.uwetrottmann.trakt5.entities.List updateList(
            @Path("username") Username username,
            @Path("id") String id,
            @Body com.uwetrottmann.trakt5.entities.List list
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Remove a custom list and all items it contains.
     */
    @DELETE("/users/{username}/lists/{id}")
    Response deleteList(
            @Path("username") Username username,
            @Path("id") String id
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Get all items on a custom list. Items can be movies, shows, seasons, episodes, or people.
     */
    @GET("/users/{username}/lists/{id}/items")
    List<ListEntry> listItems(
            @Path("username") Username username,
            @Path("id") String id,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Add one or more items to a custom list. Items can be movies, shows, seasons, episodes, or people.
     */
    @POST("/users/{username}/lists/{id}/items")
    SyncResponse addListItems(
            @Path("username") Username username,
            @Path("id") String id,
            @Body SyncItems items
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Remove one or more items from a custom list.
     */
    @POST("/users/{username}/lists/{id}/items/remove")
    SyncResponse deleteListItems(
            @Path("username") Username username,
            @Path("id") String id,
            @Body SyncItems items
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p>If the user has a private profile, the follow request will require approval (approved_at will be null). If a
     * user is public, they will be followed immediately (approved_at will have a date).
     *
     * <p>Note: If this user is already being followed, a 409 HTTP status code will returned.
     */
    @POST("/users/{username}/follow")
    Followed follow(
            @Path("username") Username username
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p>Unfollow someone you already follow.
     */
    @DELETE("/users/{username}/follow")
    Response unfollow(
            @Path("username") Username username
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p>Returns all followers including when the relationship began.
     */
    @GET("/users/{username}/followers")
    List<Follower> followers(
            @Path("username") Username username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p>Returns all user's they follow including when the relationship began.
     */
    @GET("/users/{username}/following")
    List<Follower> following(
            @Path("username") Username username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p>Returns all friends for a user including when the relationship began. Friendship is a 2 way relationship where
     * each user follows the other.
     */
    @GET("/users/{username}/friends")
    List<Friend> friends(
            @Path("username") Username username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p>Returns movies and episodes that a user has watched, sorted by most recent.
     *
     * <p>The {@code id} uniquely identifies each history event and can be used to remove events individually using the
     * {@code POST /sync/history/remove method}. The action will be set to {@code scrobble}, {@code checkin}, or {@code
     * watch}.
     *
     * @param username Example: "sean".
     */
    @GET("/users/{username}/history")
    List<HistoryEntry> history(
            @Path("username") Username username,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p>Returns movies or episodes that a user has watched, sorted by most recent.
     *
     * <p>The {@code id} uniquely identifies each history event and can be used to remove events individually using the
     * {@code POST /sync/history/remove method}. The action will be set to {@code scrobble}, {@code checkin}, or {@code
     * watch}.
     *
     * @param username Example: "sean".
     */
    @GET("/users/{username}/history/{type}")
    List<HistoryEntry> history(
            @Path("username") Username username,
            @Path("type") HistoryType type,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p>Returns the history for just the specified item. For example, {@code /history/movies/12601} would return all
     * watches for TRON: Legacy and {@code /history/shows/1388} would return all watched episodes for Breaking Bad. If
     * an invalid {@code id} is sent, a 404 error will be returned. If the {@code id} is valid, but there is no history,
     * an empty array will be returned.
     *
     * <p>The {@code id} uniquely identifies each history event and can be used to remove events individually using the
     * {@code POST /sync/history/remove method}. The action will be set to {@code scrobble}, {@code checkin}, or {@code
     * watch}.
     *
     * @param username Example: "sean".
     */
    @GET("/users/{username}/history/{type}/{id}")
    List<HistoryEntry> history(
            @Path("username") Username username,
            @Path("type") HistoryType type,
            @Path("id") int id,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Get a user's ratings filtered by movies. You can filter for a specific rating between 1 and 10.
     *
     * @param username Example: "sean".
     * @param filter Filter for a specific rating.
     */
    @GET("/users/{username}/ratings/movies{rating}")
    List<RatedMovie> ratingsMovies(
            @Path("username") Username username,
            @Path(value = "rating", encode = false) RatingsFilter filter,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Get a user's ratings filtered by shows. You can filter for a specific rating between 1 and 10.
     *
     * @param username Example: "sean".
     * @param filter Filter for a specific rating.
     */
    @GET("/users/{username}/ratings/shows{rating}")
    List<RatedShow> ratingsShows(
            @Path("username") Username username,
            @Path(value = "rating", encode = false) RatingsFilter filter,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Get a user's ratings filtered by seasons. You can filter for a specific rating between 1 and 10.
     *
     * @param username Example: "sean".
     * @param filter Filter for a specific rating.
     */
    @GET("/users/{username}/ratings/seasons{rating}")
    List<RatedSeason> ratingsSeasons(
            @Path("username") Username username,
            @Path(value = "rating", encode = false) RatingsFilter filter,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Get a user's ratings filtered by episodes. You can filter for a specific rating between 1 and 10.
     *
     * @param username Example: "sean".
     * @param filter Filter for a specific rating.
     */
    @GET("/users/{username}/ratings/episodes{rating}")
    List<RatedEpisode> ratingsEpisodes(
            @Path("username") Username username,
            @Path(value = "rating", encode = false) RatingsFilter filter,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p>Returns all items in a user's watchlist filtered by movies. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("/users/{username}/watchlist/movies")
    List<BaseMovie> watchlistMovies(
            @Path("username") Username username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p>Returns all items in a user's watchlist filtered by shows. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("/users/{username}/watchlist/shows")
    List<BaseShow> watchlistShows(
            @Path("username") Username username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p>Returns all items in a user's watchlist filtered by seasons. When an item is watched, it will be automatically
     * removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("/users/{username}/watchlist/seasons")
    List<WatchlistedSeason> watchlistSeasons(
            @Path("username") Username username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p>Returns all items in a user's watchlist filtered by episodes. When an item is watched, it will be
     * automatically removed from the watchlist. To track what the user is actively watching, use the progress APIs.
     */
    @GET("/users/{username}/watchlist/episodes")
    List<WatchlistedEpisode> watchlistEpisodes(
            @Path("username") Username username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Returns all movies or shows a user has watched sorted by most plays.
     *
     * @param username Example: "sean".
     */
    @GET("/users/{username}/watched/movies")
    List<BaseMovie> watchedMovies(
            @Path("username") Username username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Returns all movies or shows a user has watched sorted by most plays.
     *
     * @param username Example: "sean".
     */
    @GET("/users/{username}/watched/shows")
    List<BaseShow> watchedShows(
            @Path("username") Username username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

}
