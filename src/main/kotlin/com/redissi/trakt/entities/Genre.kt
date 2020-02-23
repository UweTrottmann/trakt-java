package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Genre(
    var name: String? = null,
    var slug: String? = null
)