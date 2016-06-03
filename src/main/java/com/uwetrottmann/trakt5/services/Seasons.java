package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.entities.Comment;
import com.uwetrottmann.trakt5.entities.Episode;
import com.uwetrottmann.trakt5.entities.Ratings;
import com.uwetrottmann.trakt5.entities.Season;
import com.uwetrottmann.trakt5.enums.Extended;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface Seasons {

    /**
     * Returns all seasons for a show including the number of episodes in each season.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("/shows/{id}/seasons")
    Call<List<Season>> summary(
            @Path("id") String showId,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Returns all episodes for a specific season of a show.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     */
    @GET("/shows/{id}/seasons/{season}")
    Call<List<Episode>> season(
            @Path("id") String showId,
            @Path("season") int season,
            @Query(value = "extended", encoded = true) Extended extended
    );

    /**
     * Returns all top level comments for a season. Most recent comments returned first.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     */
    @GET("/shows/{id}/seasons/{season}/comments")
    Call<List<Comment>> comments(
            @Path("id") String showId,
            @Path("season") int season
    );

    /**
     * Returns rating (between 0 and 10) and distribution for a season.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     */
    @GET("/shows/{id}/seasons/{season}/ratings")
    Call<Ratings> ratings(
            @Path("id") String showId,
            @Path("season") int season
    );

}
