package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class BaseSeason(
    val number: Int? = null,
    val episodes: List<BaseEpisode>? = null,
    /** progress  */
    val aired: Int? = null,
    /** progress  */
    val completed: Int? = null
)