package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Comment;
import com.uwetrottmann.trakt.v2.entities.Movie;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.List;

public interface Movies {

    /**
     * Returns a single movie's details.
     *
     * @param movieId trakt ID, trakt slug, or IMDB ID. Example: "tron-legacy-2010".
     */
    @GET("/movies/{id}")
    Movie summary(
            @Path("id") String movieId
    );

    /**
     * Returns all top level comments for a movie. Most recent comments returned first.
     *
     * @param movieId trakt ID, trakt slug, or IMDB ID. Example: "tron-legacy-2010".
     */
    @GET("/movies/{id}/comments")
    List<Comment> comments(
            @Path("id") String movieId
    );

}
