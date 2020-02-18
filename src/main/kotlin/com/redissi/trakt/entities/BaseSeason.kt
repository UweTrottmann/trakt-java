package com.redissi.trakt.entities

class BaseSeason(
    val number: Int? = null,
    val episodes: List<BaseEpisode>? = null,
    /** progress  */
    val aired: Int? = null,
    /** progress  */
    val completed: Int? = null
)