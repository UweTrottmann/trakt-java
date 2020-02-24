package com.redissi.trakt.enums

enum class IdType(private val value: String) : TraktEnum {
    TRAKT("trakt"), IMDB("imdb"), TMDB("tmdb"), TVDB("tvdb"), TVRAGE("tvrage");

    override fun toString(): String {
        return value
    }

}