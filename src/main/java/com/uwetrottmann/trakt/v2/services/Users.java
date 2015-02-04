package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.BaseMovie;
import com.uwetrottmann.trakt.v2.entities.BaseShow;
import com.uwetrottmann.trakt.v2.entities.Followed;
import com.uwetrottmann.trakt.v2.entities.Follower;
import com.uwetrottmann.trakt.v2.entities.Friend;
import com.uwetrottmann.trakt.v2.entities.HistoryEntry;
import com.uwetrottmann.trakt.v2.entities.ListEntry;
import com.uwetrottmann.trakt.v2.entities.RatedEpisode;
import com.uwetrottmann.trakt.v2.entities.RatedMovie;
import com.uwetrottmann.trakt.v2.entities.RatedSeason;
import com.uwetrottmann.trakt.v2.entities.RatedShow;
import com.uwetrottmann.trakt.v2.entities.Settings;
import com.uwetrottmann.trakt.v2.entities.SyncItems;
import com.uwetrottmann.trakt.v2.entities.SyncResponse;
import com.uwetrottmann.trakt.v2.entities.User;
import com.uwetrottmann.trakt.v2.enums.Extended;
import com.uwetrottmann.trakt.v2.enums.RatingsFilter;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
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
            @Path("username") String username,
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
            @Path("username") String username,
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
            @Path("username") String username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Returns all custom lists for a user.
     */
    @GET("/users/{username}/lists")
    List<com.uwetrottmann.trakt.v2.entities.List> lists(
            @Path("username") String username
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Create a new custom list. The name is the only required field, but the other info is recommended to ask for.
     */
    @POST("/users/{username}/lists")
    com.uwetrottmann.trakt.v2.entities.List createList(
            @Path("username") String username,
            @Body com.uwetrottmann.trakt.v2.entities.List list
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Update a custom list by sending 1 or more parameters. If you update the list name, the original slug will
     * still be retained so existing references to this list won't break.
     */
    @PUT("/users/{username}/lists/{id}")
    com.uwetrottmann.trakt.v2.entities.List updateList(
            @Path("username") String username,
            @Path("id") String id,
            @Body com.uwetrottmann.trakt.v2.entities.List list
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Remove a custom list and all items it contains.
     */
    @DELETE("/users/{username}/lists/{id}")
    Response deleteList(
            @Path("username") String username,
            @Path("id") String id
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Get all items on a custom list. Items can be movies, shows, seasons, episodes, or people.
     */
    @GET("/users/{username}/lists/{id}/items")
    List<ListEntry> listItems(
            @Path("username") String username,
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
            @Path("username") String username,
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
            @Path("username") String username,
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
            @Path("username") String username
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p>Unfollow someone you already follow.
     */
    @DELETE("/users/{username}/follow")
    Response unfollow(
            @Path("username") String username
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p>Returns all followers including when the relationship began.
     */
    @GET("/users/{username}/followers")
    List<Follower> followers(
            @Path("username") String username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p>Returns all user's they follow including when the relationship began.
     */
    @GET("/users/{username}/following")
    List<Follower> following(
            @Path("username") String username,
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
            @Path("username") String username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Returns episodes that a user has watched with the most recent first.
     *
     * @param username Example: "sean".
     * @param page Number of page of results to be returned. If {@code null} defaults to 1.
     * @param limit Number of results to return per page. If {@code null} defaults to 10.
     */
    @GET("/users/{username}/history/episodes")
    List<HistoryEntry> historyEpisodes(
            @Path("username") String username,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Returns movies that a user has watched with the most recent first.
     *
     * @param username Example: "sean".
     * @param page Number of page of results to be returned. If {@code null} defaults to 1.
     * @param limit Number of results to return per page. If {@code null} defaults to 10.
     */
    @GET("/users/{username}/history/movies")
    List<HistoryEntry> historyMovies(
            @Path("username") String username,
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
            @Path("username") String username,
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
            @Path("username") String username,
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
            @Path("username") String username,
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
            @Path("username") String username,
            @Path(value = "rating", encode = false) RatingsFilter filter,
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
            @Path("username") String username,
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
            @Path("username") String username,
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

}
