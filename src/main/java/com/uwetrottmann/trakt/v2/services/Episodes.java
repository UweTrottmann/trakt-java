package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Comment;
import com.uwetrottmann.trakt.v2.entities.Episode;
import com.uwetrottmann.trakt.v2.entities.Ratings;
import com.uwetrottmann.trakt.v2.entities.Translation;
import com.uwetrottmann.trakt.v2.enums.Extended;
import retrofit.http.EncodedQuery;
import retrofit.http.GET;
import retrofit.http.Path;

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
     * Returns a single translation for a show. If the translation does not exist, the returned list will be empty.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     * @param episode Episode number.
     * @param language 2-letter language code (ISO 639-1).
     */
    @GET("/shows/{id}/seasons/{season}/episodes/{episode}/translations/{language}")
    List<Translation> translation(
            @Path("id") String showId,
            @Path("season") int season,
            @Path("episode") int episode,
            @Path("language") String language
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
