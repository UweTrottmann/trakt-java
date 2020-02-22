package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SeasonIds(
    val tvdb: Int? = null,
    val tmdb: Int? = null,
    val trakt: Int? = null,
    val tvrage: Int? = null
)