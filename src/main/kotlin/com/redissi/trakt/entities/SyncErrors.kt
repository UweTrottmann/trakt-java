package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SyncErrors(
    val movies: List<SyncMovie>? = null,
    val shows: List<SyncShow>? = null,
    val seasons: List<SyncSeason>? = null,
    val episodes: List<SyncEpisode>? = null,
    val people: List<SyncPerson>? = null,
    val ids: List<Long>? = null
)