package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Show;
import retrofit.http.GET;
import retrofit.http.Path;

public interface Shows {

    /**
     * Returns a single shows's details.
     *
     * @param traktId A trakt id.
     */
    @GET("/shows/{id}")
    Show summary(
            @Path("id") int traktId
    );

    /**
     * Returns a single shows's details.
     *
     * @param slugOrImdbId A trakt slug or an IMDB id.
     */
    @GET("/shows/{id}")
    Show summary(
            @Path("id") String slugOrImdbId
    );

}
