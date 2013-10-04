package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.TvShow;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Endpoints for Show.
 */
public interface Show {

    @GET("/show/summary.json/{apikey}/{title}")
    TvShow summary(
            @Path("title") String slug
    );

    @GET("/show/summary.json/{apikey}/{title}")
    TvShow summary(
            @Path("title") int tvdbId
    );

    @GET("/show/summary.json/{apikey}/{title}/extended")
    TvShow summaryExtended(
            @Path("title") String slug
    );

    @GET("/show/summary.json/{apikey}/{title}/extended")
    TvShow summaryExtended(
            @Path("title") int tvdbId
    );

}
