package com.redissi.trakt.services

import com.redissi.trakt.entities.EpisodeCheckin
import com.redissi.trakt.entities.EpisodeCheckinResponse
import com.redissi.trakt.entities.MovieCheckin
import com.redissi.trakt.entities.MovieCheckinResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface Checkin {
    /**
     * **OAuth Required**
     *
     *
     *  Check into an episode. This should be tied to a user action to manually indicate they are watching something.
     * The item will display as watching on the site, then automatically switch to watched status once the duration has
     * elapsed.
     */
    @POST("checkin")
    suspend fun checkin(
        @Body episodeCheckin: EpisodeCheckin?
    ): Response<EpisodeCheckinResponse?>

    /**
     * **OAuth Required**
     *
     *
     *  Check into a movie. This should be tied to a user action to manually indicate they are watching something.
     * The item will display as watching on the site, then automatically switch to watched status once the duration has
     * elapsed.
     */
    @POST("checkin")
    suspend fun checkin(
        @Body movieCheckin: MovieCheckin?
    ): Response<MovieCheckinResponse?>

    /**
     * **OAuth Required**
     *
     *
     *  Removes any active checkins, no need to provide a specific item.
     */
    @DELETE("checkin")
    suspend fun deleteActiveCheckin(): Response<Unit>
}