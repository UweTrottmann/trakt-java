package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.Checkin;
import com.jakewharton.trakt.entities.CheckinResponse;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.entities.TvShow;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Endpoints for Show.
 */
public interface Show {

    @POST("/show/cancelcheckin/{apikey}")
    Response cancelcheckin();

    @POST("/show/cancelwatching/{apikey}")
    Response cancelwatching();

    @POST("/show/checkin/{apikey}")
    CheckinResponse checkin(
            @Body Checkin checkin
    );

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
