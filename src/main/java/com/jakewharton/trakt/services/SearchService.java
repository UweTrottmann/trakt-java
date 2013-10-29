package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.TvShow;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

public interface SearchService {

    @GET("/search/shows.json/{apikey}/{query}")
    List<TvShow> shows(
            @Path("query") String query
    );

    @GET("/search/shows.json/{apikey}/{query}/{limit}")
    List<TvShow> shows(
            @Path("query") String query,
            @Path("limit") int limit
    );

    @GET("/search/shows.json/{apikey}/{query}/{limit}/seasons")
    List<TvShow> showsWithSeasons(
            @Path("query") String query,
            @Path("limit") int limit
    );

}
