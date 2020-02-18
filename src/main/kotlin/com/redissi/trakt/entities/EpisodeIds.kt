package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class EpisodeIds(
    override val trakt: Int? = null,
    override val imdb: String? = null,
    override val tmdb: Int? = null,
    val tvdb: Int? = null,
    val tvrage: Int? = null
) : BaseIds() {

    companion object {
        fun trakt(trakt: Int): EpisodeIds {
            return EpisodeIds(trakt = trakt)
        }

        fun imdb(imdb: String?): EpisodeIds {
            return EpisodeIds(imdb = imdb)
        }

        fun tmdb(tmdb: Int): EpisodeIds {
            return EpisodeIds(tmdb = tmdb)
        }

        fun tvdb(tvdb: Int): EpisodeIds {
            return EpisodeIds(tvdb = tvdb)
        }

        fun tvrage(tvrage: Int): EpisodeIds {
            return EpisodeIds(tvrage = tvrage)
        }
    }
}