package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class TrendingMovie(
    override val watchers: Int? = null,
    val movie: Movie? = null
) : BaseTrendingEntity()