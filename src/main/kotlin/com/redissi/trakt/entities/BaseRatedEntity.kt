package com.redissi.trakt.entities

import com.redissi.trakt.enums.Rating
import com.squareup.moshi.Json
import java.time.OffsetDateTime

open class BaseRatedEntity(
    @Json(name = "rated_at")
    val ratedAt: OffsetDateTime? = null,
    val rating: Rating? = null
)