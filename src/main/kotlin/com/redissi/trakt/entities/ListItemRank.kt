package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ListItemRank(
    val rank: List<Long>? = null
)