package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class Episode(
    override val title: String? = null,
    override val overview: String? = null,
    override val rating: Double? = null,
    override val votes: Int? = null,
    override val updatedAt: OffsetDateTime? = null,
    override val availableTranslations: List<String>? = null,
    val season: Int? = null,
    val number: Int? = null,
    val ids: EpisodeIds? = null,
    // extended info
    @Json(name = "number_abs")
    val number_abs: Int? = null,
    @Json(name = "first_aired")
    val firstAired: OffsetDateTime? = null,
    @Json(name = "comment_count")
    val commentCount: Int? = null,
    val runtime: Int? = null
) : BaseEntity()