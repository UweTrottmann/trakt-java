package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PersonIds(
    override val trakt: Int? = null,
    override val imdb: String? = null,
    override val tmdb: Int? = null,
    val slug: String? = null,
    val tvrage: String? = null
) : BaseIds() {

    companion object {
        fun trakt(trakt: Int): PersonIds {
            return PersonIds(trakt = trakt)
        }

        fun imdb(imdb: String?): PersonIds {
            return PersonIds(imdb = imdb)
        }

        fun tmdb(tmdb: Int): PersonIds {
            return PersonIds(tmdb = tmdb)
        }

        fun slug(slug: String?): PersonIds {
            return PersonIds(slug = slug)
        }
    }
}