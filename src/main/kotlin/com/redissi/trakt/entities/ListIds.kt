package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ListIds(
    val trakt: Int? = null,
    val slug: String? = null
) {

    companion object {
        fun trakt(trakt: Int): ListIds {
            return ListIds(trakt = trakt)
        }

        fun slug(slug: String?): ListIds {
            return ListIds(slug = slug)
        }
    }
}