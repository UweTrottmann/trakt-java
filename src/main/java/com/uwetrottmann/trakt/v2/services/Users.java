package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.BaseMovie;
import com.uwetrottmann.trakt.v2.entities.BaseShow;
import com.uwetrottmann.trakt.v2.entities.EpisodeHistoryEntry;
import com.uwetrottmann.trakt.v2.entities.MovieHistoryEntry;
import com.uwetrottmann.trakt.v2.entities.RatedEpisode;
import com.uwetrottmann.trakt.v2.entities.RatedMovie;
import com.uwetrottmann.trakt.v2.entities.RatedSeason;
import com.uwetrottmann.trakt.v2.entities.RatedShow;
import com.uwetrottmann.trakt.v2.entities.Settings;
import com.uwetrottmann.trakt.v2.entities.User;
import com.uwetrottmann.trakt.v2.enums.Extended;
import com.uwetrottmann.trakt.v2.enums.RatingsFilter;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.EncodedPath;
import retrofit.http.EncodedQuery;
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
            @EncodedQuery("extended") Extended extended
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
            @EncodedQuery("extended") Extended extended
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
            @EncodedQuery("extended") Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Returns all custom lists for a user.
     *
     * @param username Example: "sean".
     */
    @GET("/users/{username}/lists")
    List<com.uwetrottmann.trakt.v2.entities.List> lists(
            @Path("username") String username
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Create a new custom list. The name is the only required field, but the other info is recommended to ask for.
     *
     * @param username Example: "sean".
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
     *
     * @param username Example: "sean".
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
     *
     * @param username Example: "sean".
     */
    @DELETE("/users/{username}/lists/{id}")
    Response deleteList(
            @Path("username") String username,
            @Path("id") String id
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
    List<EpisodeHistoryEntry> historyEpisodes(
            @Path("username") String username,
            @Query("page") Integer page,
            @Query("limit") Integer limit
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
    List<MovieHistoryEntry> historyMovies(
            @Path("username") String username,
            @Query("page") Integer page,
            @Query("limit") Integer limit
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
            @EncodedPath("rating") RatingsFilter filter,
            @EncodedQuery("extended") Extended extended
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
            @EncodedPath("rating") RatingsFilter filter,
            @EncodedQuery("extended") Extended extended
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
            @EncodedPath("rating") RatingsFilter filter,
            @EncodedQuery("extended") Extended extended
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
            @EncodedPath("rating") RatingsFilter filter,
            @EncodedQuery("extended") Extended extended
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
            @EncodedQuery("extended") Extended extended
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
            @EncodedQuery("extended") Extended extended
    ) throws OAuthUnauthorizedException;

}
