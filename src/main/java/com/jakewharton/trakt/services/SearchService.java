package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvShow;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.List;

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

    @GET("/search/movies.json/{apikey}/{query}")
    List<Movie> movies(
            @Path("query") String query
    );

    @GET("/search/movies.json/{apikey}/{query}/{limit}")
    List<Movie> movies(
            @Path("query") String query,
            @Path("limit") int limit
    );

}
