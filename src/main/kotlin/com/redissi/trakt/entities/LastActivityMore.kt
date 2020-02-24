package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class LastActivityMore(
    @Json(name = "rated_at")
    ratedAt: OffsetDateTime? = null,
    @Json(name = "watchlisted_at")
    watchlistedAt: OffsetDateTime? = null,
    @Json(name = "commented_at")
    commentedAt: OffsetDateTime? = null,
    @Json(name = "watched_at")
    val watchedAt: OffsetDateTime? = null,
    @Json(name = "collected_at")
    val collectedAt: OffsetDateTime? = null,
    @Json(name = "paused_at")
    val pausedAt: OffsetDateTime? = null,
    @Json(name = "hidden_at")
    val hiddenAt: OffsetDateTime? = null
) : LastActivity(ratedAt, watchlistedAt, commentedAt)