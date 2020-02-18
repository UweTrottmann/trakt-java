package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Airs(
    val day: String? = null,
    val time: String? = null,
    val timezone: String? = null
)