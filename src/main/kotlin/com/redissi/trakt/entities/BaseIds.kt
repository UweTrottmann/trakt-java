package com.redissi.trakt.entities

abstract class BaseIds(
    open val trakt: Int? = null,
    open val imdb: String? = null,
    open val tmdb: Int? = null
)