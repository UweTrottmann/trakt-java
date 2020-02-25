package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class Friend(
    @Json(name = "friends_at")
    val friendsAt: OffsetDateTime? = null,
    val user: User? = null
)