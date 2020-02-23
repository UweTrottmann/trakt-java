package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class CalendarShowEntry(
    @Json(name = "first_aired")
    val firstAired: OffsetDateTime? = null,
    val episode: Episode? = null,
    val show: Show? = null
)