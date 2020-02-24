package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
open class LastActivity(
    @Json(name = "rated_at")
    val ratedAt: OffsetDateTime? = null,
    @Json(name = "watchlisted_at")
    val watchlistedAt: OffsetDateTime? = null,
    @Json(name = "commented_at")
    val commentedAt: OffsetDateTime? = null
)