package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Account(
    var timezone: String? = null,
    @Json(name = "cover_image")
    var coverImage: String? = null
)