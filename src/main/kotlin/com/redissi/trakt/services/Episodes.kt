package com.redissi.trakt.services

import com.redissi.trakt.entities.Comment
import com.redissi.trakt.entities.Episode
import com.redissi.trakt.entities.Ratings
import com.redissi.trakt.entities.Stats
import com.redissi.trakt.enums.Extended
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Episodes {
    /**
     * Returns a single episode's details.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     * @param episode Episode number.
     */
    @GET("shows/{id}/seasons/{season}/episodes/{episode}")
    suspend fun summary(
        @Path("id") showId: String?,
        @Path("season") season: Int,
        @Path("episode") episode: Int,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): Episode?

    /**
     * Returns all top level comments for an episode. Most recent comments returned first.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     * @param episode Episode number.
     */
    @GET("shows/{id}/seasons/{season}/episodes/{episode}/comments")
    suspend fun comments(
        @Path("id") showId: String?,
        @Path("season") season: Int,
        @Path("episode") episode: Int,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<Comment?>?

    /**
     * Returns rating (between 0 and 10) and distribution for an episode.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     * @param episode Episode number.
     */
    @GET("shows/{id}/seasons/{season}/episodes/{episode}/ratings")
    suspend fun ratings(
        @Path("id") showId: String?,
        @Path("season") season: Int,
        @Path("episode") episode: Int
    ): Ratings?

    /**
     * Returns lots of episode stats.
     */
    @GET("shows/{id}/seasons/{season}/episodes/{episode}/stats")
    suspend fun stats(
        @Path("id") showId: String?,
        @Path("season") season: Int,
        @Path("episode") episode: Int
    ): Stats?
}