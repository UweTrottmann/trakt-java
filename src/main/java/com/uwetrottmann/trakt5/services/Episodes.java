package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.entities.Comment;
import com.uwetrottmann.trakt5.entities.Episode;
import com.uwetrottmann.trakt5.entities.Ratings;
import com.uwetrottmann.trakt5.enums.Extended;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Call<Episode> summary(
            @Path("id") String showId,
            @Path("season") int season,
            @Path("episode") int episode,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Returns all top level comments for an episode. Most recent comments returned first.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     * @param episode Episode number.
     */
    @GET("/shows/{id}/seasons/{season}/episodes/{episode}/comments")
    Call<List<Comment>> comments(
            @Path("id") String showId,
            @Path("season") int season,
            @Path("episode") int episode,
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Returns rating (between 0 and 10) and distribution for an episode.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     * @param episode Episode number.
     */
    @GET("/shows/{id}/seasons/{season}/episodes/{episode}/ratings")
    Call<Ratings> ratings(
            @Path("id") String showId,
            @Path("season") int season,
            @Path("episode") int episode
    );

}
