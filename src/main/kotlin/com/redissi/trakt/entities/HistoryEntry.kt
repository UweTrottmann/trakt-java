package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class HistoryEntry(
    val id: Long? = null,
    @Json(name = "watched_at")
    val watchedAt: OffsetDateTime? = null,
    val action: String? = null,
    val type: String? = null,
    val episode: Episode? = null,
    val show: Show? = null,
    val movie: Movie? = null
)