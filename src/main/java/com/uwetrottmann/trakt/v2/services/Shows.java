package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Show;
import retrofit.http.GET;
import retrofit.http.Path;

public interface Shows {

    @GET("/shows/{id}")
    Show summary(
            @Path("id") int traktId
    );

    @GET("/shows/{id}")
    Show summary(
            @Path("id") String slugOrImdbId
    );

}
