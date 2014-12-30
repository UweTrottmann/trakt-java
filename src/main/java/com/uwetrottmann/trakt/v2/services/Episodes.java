package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Comment;
import com.uwetrottmann.trakt.v2.entities.Episode;
import com.uwetrottmann.trakt.v2.entities.Ratings;
import com.uwetrottmann.trakt.v2.enums.Extended;
import retrofit.http.EncodedQuery;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

public interface Episodes {

    /**
     * Returns a single episode's details.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     * @param episode Episode number.
     */
    @GET("/shows/{id}/seasons/{season}/episodes/{episode}")
    Episode summary(
            @Path("id") String showId,
            @Path("season") int season,
            @Path("episode") int episode,
            @EncodedQuery("extended") Extended extended
    );

    /**
     * Returns all top level comments for an episode. Most recent comments returned first.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     * @param episode Episode number.
     */
    @GET("/shows/{id}/seasons/{season}/episodes/{episode}/comments")
    List<Comment> comments(
            @Path("id") String showId,
            @Path("season") int season,
            @Path("episode") int episode,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @EncodedQuery("extended") Extended extended
    );

    /**
     * Returns rating (between 0 and 10) and distribution for an episode.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     * @param episode Episode number.
     */
    @GET("/shows/{id}/seasons/{season}/episodes/{episode}/ratings")
    Ratings ratings(
            @Path("id") String showId,
            @Path("season") int season,
            @Path("episode") int episode
    );

}
