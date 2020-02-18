package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass
import java.time.LocalDate
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class Movie(
    override val title: String? = null,
    override val overview: String? = null,
    override val rating: Double? = null,
    override val votes: Int? = null,
    override val updatedAt: OffsetDateTime? = null,
    override val availableTranslations: List<String>? = null,
    val year: Int? = null,
    val ids: MovieIds? = null,
    // extended info
    val certification: String? = null,
    val tagline: String? = null,
    /** Date in UTC time.  */
    val released: LocalDate? = null,
    val runtime: Int? = null,
    val trailer: String? = null,
    val homepage: String? = null,
    val language: String? = null,
    val genres: List<String>? = null
) : BaseEntity()