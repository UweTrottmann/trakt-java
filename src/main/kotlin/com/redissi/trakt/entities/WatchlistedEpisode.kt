package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class WatchlistedEpisode(
    @Json(name = "listed_at")
    val listedAt: OffsetDateTime? = null,
    val episode: Episode? = null,
    val show: Show? = null
)