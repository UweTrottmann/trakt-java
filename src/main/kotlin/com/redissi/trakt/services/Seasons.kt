package com.redissi.trakt.services

import com.redissi.trakt.entities.*
import com.redissi.trakt.enums.Extended
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Seasons {
    /**
     * Returns all seasons for a show including the number of episodes in each season.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("shows/{id}/seasons")
    suspend fun summary(
        @Path("id") showId: String?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<Season>?

    /**
     * Returns all episodes for a specific season of a show.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     */
    @GET("shows/{id}/seasons/{season}")
    suspend fun season(
        @Path("id") showId: String?,
        @Path("season") season: Int,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<Episode>?

    /**
     * Returns all top level comments for a season. Most recent comments returned first.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     */
    @GET("shows/{id}/seasons/{season}/comments")
    suspend fun comments(
        @Path("id") showId: String?,
        @Path("season") season: Int
    ): List<Comment>?

    /**
     * Returns rating (between 0 and 10) and distribution for a season.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param season Season number.
     */
    @GET("shows/{id}/seasons/{season}/ratings")
    suspend fun ratings(
        @Path("id") showId: String?,
        @Path("season") season: Int
    ): Ratings?

    /**
     * Returns lots of season stats.
     */
    @GET("shows/{id}/seasons/{season}/stats")
    suspend fun stats(
        @Path("id") showId: String?,
        @Path("season") season: Int
    ): Stats?
}