package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Movie;
import retrofit.http.GET;
import retrofit.http.Path;

public interface Movies {

    /**
     * Returns a single movie's details.
     *
     * @param movieId trakt ID, trakt slug, or IMDB ID. Example: "tron-legacy-2010".
     */
    @GET("/movies/{id}")
    Movie summary(
            @Path("id") String movieId
    );

}
