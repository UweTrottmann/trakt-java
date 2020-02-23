package com.redissi.trakt.services

import com.redissi.trakt.entities.Genre
import retrofit2.http.GET

interface Genres {
    /**
     * Get a list of all genres for shows, including names and slugs.
     */
    @GET("genres/movies")
    suspend fun movies(): List<Genre>?

    /**
     * Get a list of all genres for movies, including names and slugs.
     */
    @GET("genres/shows")
    suspend fun shows(): List<Genre>?
}