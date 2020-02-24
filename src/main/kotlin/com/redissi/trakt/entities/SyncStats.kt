package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SyncStats(
    val movies: Int? = null,
    val shows: Int? = null,
    val seasons: Int? = null,
    val episodes: Int? = null,
    val people: Int? = null
)