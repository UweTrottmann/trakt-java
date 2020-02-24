package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class LastActivities(
    val all: OffsetDateTime? = null,
    val movies: LastActivityMore? = null,
    val episodes: LastActivityMore? = null,
    val shows: LastActivity? = null,
    val seasons: LastActivity? = null,
    val lists: ListsLastActivity? = null
)