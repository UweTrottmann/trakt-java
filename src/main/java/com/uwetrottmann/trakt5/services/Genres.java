package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.entities.Genre;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface Genres {

    /**
     * Get a list of all genres for shows, including names and slugs.
     */
    @GET("/genres/movies")
    Call<List<Genre>> movies();

    /**
     * Get a list of all genres for movies, including names and slugs.
     */
    @GET("/genres/shows")
    Call<List<Genre>> shows();

}
