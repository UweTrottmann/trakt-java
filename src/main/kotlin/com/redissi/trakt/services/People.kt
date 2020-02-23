package com.redissi.trakt.services

import com.redissi.trakt.entities.Credits
import com.redissi.trakt.entities.Person
import com.redissi.trakt.enums.Extended
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface People {
    /**
     * Returns a single person's details.
     *
     * @param personId trakt ID, trakt slug, or IMDB ID Example: bryan-cranston.
     */
    @GET("people/{id}")
    suspend fun summary(
        @Path("id") personId: String?,
        @Query("extended") extended: Extended?
    ): Person?

    @GET("people/{id}/movies")
    suspend fun movieCredits(
        @Path("id") personId: String?
    ): Credits?

    @GET("people/{id}/shows")
    suspend fun showCredits(
        @Path("id") personId: String?
    ): Credits?
}