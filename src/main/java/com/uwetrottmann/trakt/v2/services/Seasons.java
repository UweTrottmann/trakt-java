package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Season;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.List;

public interface Seasons {

    /**
     * Returns all seasons for a show including the number of episodes in each season.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("/shows/{id}/seasons")
    List<Season> summary(
            @Path("id") String showId
    );

}
