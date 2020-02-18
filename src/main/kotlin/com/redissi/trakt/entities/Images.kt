package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Images(
    val avatar: ImageSizes? = null
) {

    @JsonClass(generateAdapter = true)
    class ImageSizes {
        var full: String? = null
    }
}