package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Settings;
import com.uwetrottmann.trakt.v2.entities.User;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

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
     * <p> Get a list of recent activity for a user. Activities returned include: checkins, scrobbles, ratings,
     * collections, new lists and comments.
     *
     * @param username Example: "sean".
     */
    @GET("/users/{username}/activities")
    Response activities(
            @Path("username") String username
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Get a list of recent activity for your friends. Activities returned include: checkins, scrobbles, ratings,
     * collections, new lists and comments.
     *
     * @param username Example: "sean".
     */
    @GET("/users/{username}/activities/friends")
    Response friendActivities(
            @Path("username") String username
    ) throws OAuthUnauthorizedException;

}
