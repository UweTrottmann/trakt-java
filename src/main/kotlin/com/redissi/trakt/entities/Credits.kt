package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Credits(
    val cast: List<CastMember>? = null,
    val crew: Crew? = null
)