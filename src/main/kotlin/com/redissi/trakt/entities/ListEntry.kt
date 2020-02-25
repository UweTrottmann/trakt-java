package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class ListEntry(
    val id: Long? = null,
    val rank: Int? = null,
    @Json(name = "listed_at")
    val listedAt: OffsetDateTime? = null,
    val type: String? = null,
    val movie: Movie? = null,
    val show: Show? = null,
    val episode: Episode? = null,
    val person: Person? = null
)