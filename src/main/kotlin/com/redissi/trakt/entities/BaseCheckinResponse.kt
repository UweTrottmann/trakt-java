package com.redissi.trakt.entities

import com.squareup.moshi.Json
import java.time.OffsetDateTime

abstract class BaseCheckinResponse {
    @Json(name = "watched_at")
    abstract val watchedAt: OffsetDateTime?
    abstract val sharing: ShareSettings?
}