package com.redissi.trakt.entities

import com.redissi.trakt.enums.Rating
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class SyncEpisode(
    val season: Int? = null,
    val number: Int? = null,
    val ids: EpisodeIds? = null,
    @Json(name = "collected_at")
    val collectedAt: OffsetDateTime? = null,
    @Json(name = "watched_at")
    val watchedAt: OffsetDateTime? = null,
    @Json(name = "rated_at")
    val ratedAt: OffsetDateTime? = null,
    val rating: Rating? = null
)