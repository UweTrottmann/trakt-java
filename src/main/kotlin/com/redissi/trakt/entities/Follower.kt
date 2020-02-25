package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class Follower(
    @Json(name = "followed_at")
    val followedAt: OffsetDateTime? = null,
    val user: User? = null
)