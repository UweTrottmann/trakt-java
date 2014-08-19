package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Show;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.List;

public interface Shows {

    /**
     * Returns the most popular shows. Popularity is calculated using the rating percentage and the number of ratings.
     */
    @GET("/shows/popular")
    List<Show> popular();

    /**
     * Returns all shows being watched right now. Shows with the most users are returned first.
     */
    @GET("/shows/trending")
    List<Show> trending();

    /**
     * Returns a single shows's details.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("/shows/{id}")
    Show summary(
            @Path("id") String showId
    );

}
