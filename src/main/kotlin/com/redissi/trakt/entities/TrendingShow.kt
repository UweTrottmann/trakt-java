package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class TrendingShow(
    override val watchers: Int? = null,
    val show: Show? = null
) : BaseTrendingEntity()