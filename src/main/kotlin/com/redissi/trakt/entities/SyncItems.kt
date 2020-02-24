package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SyncItems(
    val movies: List<SyncMovie>? = null,
    val shows: List<SyncShow>? = null,
    val episodes: List<SyncEpisode>? = null,
    val people: List<SyncPerson>? = null,
    /**
     * Only supported for removing specific history items.
     */
    val ids: List<Long>? = null
)