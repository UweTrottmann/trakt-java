package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieTranslation(
    override val language: String? = null,
    override val title: String? = null,
    override val overview: String? = null,
    val tagline: String? = null
) : Translation()