package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ShareSettings(
    val facebook: Boolean? = null,
    val twitter: Boolean? = null,
    val tumblr: Boolean? = null
)