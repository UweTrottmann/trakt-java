package com.redissi.trakt.services

import com.redissi.trakt.entities.PlaybackResponse
import com.redissi.trakt.entities.ScrobbleProgress
import retrofit2.http.Body
import retrofit2.http.POST

interface Scrobble {
    /**
     * **OAuth Required**
     *
     *
     *  User starts a video
     */
    @POST("scrobble/start")
    suspend fun startWatching(
        @Body prog: ScrobbleProgress?
    ): PlaybackResponse?

    /**
     * **OAuth Required**
     *
     *
     *  User pauses a video
     */
    @POST("scrobble/pause")
    suspend fun pauseWatching(
        @Body prog: ScrobbleProgress?
    ): PlaybackResponse?

    /**
     * **OAuth Required**
     *
     *
     *  User stops a video
     */
    @POST("scrobble/stop")
    suspend fun stopWatching(
        @Body prog: ScrobbleProgress?
    ): PlaybackResponse?
}