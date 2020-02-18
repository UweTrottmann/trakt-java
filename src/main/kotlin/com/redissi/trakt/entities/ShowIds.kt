package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ShowIds(
    override val trakt: Int? = null,
    override val imdb: String? = null,
    override val tmdb: Int? = null,
    val slug: String? = null,
    val tvdb: Int? = null,
    val tvrage: Int? = null
) : BaseIds() {

    companion object {
        fun trakt(trakt: Int): ShowIds {
            return ShowIds(trakt = trakt)
        }

        fun imdb(imdb: String?): ShowIds {
            return ShowIds(imdb = imdb)
        }

        fun tmdb(tmdb: Int): ShowIds {
            return ShowIds(tmdb = tmdb)
        }

        fun slug(slug: String?): ShowIds {
            return ShowIds(slug = slug)
        }

        fun tvdb(tvdb: Int): ShowIds {
            return ShowIds(tvdb = tvdb)
        }

        fun tvrage(tvrage: Int): ShowIds {
            return ShowIds(tvrage = tvrage)
        }
    }
}