package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class Translation(
    open val language: String? = null,
    open val title: String? = null,
    open val overview: String? = null
)