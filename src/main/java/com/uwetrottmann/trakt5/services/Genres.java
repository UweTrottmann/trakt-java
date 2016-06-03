package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.entities.Genre;
import retrofit.http.GET;

import java.util.List;

public interface Genres {

    /**
     * Get a list of all genres for shows, including names and slugs.
     */
    @GET("/genres/movies")
    List<Genre> movies();

    /**
     * Get a list of all genres for movies, including names and slugs.
     */
    @GET("/genres/shows")
    List<Genre> shows();

}
