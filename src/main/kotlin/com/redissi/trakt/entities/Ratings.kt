package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Ratings(
    val rating: Double? = null,
    val votes: Int? = null,
    var distribution: Map<String, Int>? = null
)