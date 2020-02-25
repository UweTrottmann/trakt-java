package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SharingText(
    val watching: String? = null,
    val watched: String? = null
)