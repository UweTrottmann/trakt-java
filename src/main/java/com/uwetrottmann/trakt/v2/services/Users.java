package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.EpisodeHistoryEntry;
import com.uwetrottmann.trakt.v2.entities.MovieHistoryEntry;
import com.uwetrottmann.trakt.v2.entities.Settings;
import com.uwetrottmann.trakt.v2.entities.User;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import retrofit.http.GET;
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
            @Path("username") String username
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

}
