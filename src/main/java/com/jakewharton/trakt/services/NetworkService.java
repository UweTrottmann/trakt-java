
package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.FollowResponse;

import retrofit.http.Body;
import retrofit.http.POST;

public interface NetworkService {

    /**
     * Follow a user. If the user has a protected profile, the follow request will be in a pending
     * state. If they have a public profile, they will be followed immediately.
     *
     * @param user Username of the person to follow.
     */
    @POST("/network/follow/{apikey}")
    FollowResponse follow(
            @Body User user
    );

    public static class User {

        public String user;

        public User(String username) {
            this.user = username;
        }
    }
}
