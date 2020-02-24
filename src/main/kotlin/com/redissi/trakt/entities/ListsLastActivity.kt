package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class ListsLastActivity(
    @Json(name = "liked_at")
    val likedAt: OffsetDateTime? = null,
    @Json(name = "updated_at")
    val updatedAt: OffsetDateTime? = null,
    @Json(name = "commented_at")
    val commentedAt: OffsetDateTime? = null
)