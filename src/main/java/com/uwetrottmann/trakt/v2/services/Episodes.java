package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Episode;
import retrofit.http.GET;
import retrofit.http.Path;

public interface Episodes {

    /**
     * Returns a single episode's details.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season season number.
     * @param episode episode number.
     */
    @GET("/shows/{id}/seasons/{season}/episodes/{episode}")
    Episode summary(
            @Path("id") String showId,
            @Path("season") int season,
            @Path("episode") int episode
    );

}
