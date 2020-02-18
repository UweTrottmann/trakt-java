package com.redissi.trakt.entities

import com.squareup.moshi.Json
import java.time.OffsetDateTime

class Season(
    var number: Int? = null,
    var ids: SeasonIds? = null,
    var title: String? = null,
    var overview: String? = null,
    var network: String? = null,
    @Json(name = "first_aired")
    var firstAired: OffsetDateTime? = null,
    var rating: Double? = null,
    var votes: Int? = null,
    @Json(name = "episode_count")
    var episodeCount: Int? = null,
    @Json(name = "aired_episodes")
    var airedEpisodes: Int? = null,
    var episodes: List<Episode>? = null
)