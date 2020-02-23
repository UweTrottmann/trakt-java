package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class MovieCheckinResponse(
    @Json(name = "watched_at")
    override val watchedAt: OffsetDateTime? = null,
    override val sharing: ShareSettings? = null,
    val movie: Movie? = null
) : BaseCheckinResponse()