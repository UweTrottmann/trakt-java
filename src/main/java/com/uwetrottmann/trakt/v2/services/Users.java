package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.User;
import retrofit.http.GET;
import retrofit.http.Path;

public interface Users {

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
    );

}
