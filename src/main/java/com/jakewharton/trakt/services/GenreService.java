package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.Genre;

import java.util.List;

import retrofit.http.GET;

public interface GenreService {

    /**
     * Get a list of all movie genres including names and slugs.
     */
    @GET("/genres/movies.json/{apikey}")
    List<Genre> movies();

    /**
     * Get a list of all show genres including names and slugs.
     */
    @GET("/genres/shows.json/{apikey}")
    List<Genre> shows();

}
