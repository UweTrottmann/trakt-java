package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class Followed(
    @Json(name = "approved_at")
    val approvedAt: OffsetDateTime? = null,
    val user: User? = null
)