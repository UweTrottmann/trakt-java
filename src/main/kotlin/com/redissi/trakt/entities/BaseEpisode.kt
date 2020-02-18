package com.redissi.trakt.entities

import com.squareup.moshi.Json
import java.time.OffsetDateTime

class BaseEpisode(
    val number: Int? = null,
    /** collection  */
    @Json(name = "collected_at")
    val collectedAt: OffsetDateTime? = null,
    /** watched  */
    val plays: Int? = null,
    @Json(name = "last_watched_at")
    val lastWatchedAt: OffsetDateTime? = null,
    /** progress  */
    val completed: Boolean? = null
)