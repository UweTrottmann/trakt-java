package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Comment;
import com.uwetrottmann.trakt.v2.entities.Episode;
import com.uwetrottmann.trakt.v2.entities.Season;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.List;

public interface Seasons {

    /**
     * Returns all seasons for a show including the number of episodes in each season.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("/shows/{id}/seasons")
    List<Season> summary(
            @Path("id") String showId
    );

    /**
     * Returns all episodes for a specific season of a show.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     */
    @GET("/shows/{id}/seasons/{season}")
    List<Episode> season(
            @Path("id") String showId,
            @Path("season") int season
    );

    /**
     * Returns all top level comments for a season. Most recent comments returned first.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     */
    @GET("/shows/{id}/seasons/{season}/comments")
    List<Comment> comments(
            @Path("id") String showId,
            @Path("season") int season
    );

}
