package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieIds(
    override val trakt: Int? = null,
    override val imdb: String? = null,
    override val tmdb: Int? = null,
    val slug: String? = null
) : BaseIds() {

    companion object {
        fun trakt(trakt: Int): MovieIds {
            return MovieIds(trakt = trakt)
        }

        fun imdb(imdb: String?): MovieIds {
            return MovieIds(imdb = imdb)
        }

        fun tmdb(tmdb: Int): MovieIds {
            return MovieIds(tmdb = tmdb)
        }

        fun slug(slug: String?): MovieIds {
            return MovieIds(slug = slug)
        }
    }
}