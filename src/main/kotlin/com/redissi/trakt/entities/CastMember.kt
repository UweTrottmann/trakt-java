package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CastMember(
    val character: String? = null,
    val movie: Movie? = null,
    val show: Show? = null,
    val person: Person? = null
)