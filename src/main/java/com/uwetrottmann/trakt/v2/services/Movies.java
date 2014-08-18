package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Movie;
import retrofit.http.GET;
import retrofit.http.Path;

public interface Movies {

    @GET("/movies/{id}")
    Movie summary(
            @Path("id") int traktId
    );

    @GET("/movies/{id}")
    Movie summary(
            @Path("id") String slugOrImdbId
    );

}
