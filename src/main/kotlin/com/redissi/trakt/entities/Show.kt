package com.redissi.trakt.entities

import com.redissi.trakt.enums.Status
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class Show(
    override val title: String? = null,
    override val overview: String? = null,
    override val rating: Double? = null,
    override val votes: Int? = null,
    override val updatedAt: java.time.OffsetDateTime? = null,
    override val availableTranslations: List<String>? = null,
    val year: Int? = null,
    val ids: ShowIds? = null,
    // extended info
    @Json(name = "first_aired")
    val firstAired: OffsetDateTime? = null,
    val airs: Airs? = null,
    val runtime: Int? = null,
    val certification: String? = null,
    val network: String? = null,
    val country: String? = null,
    val trailer: String? = null,
    val homepage: String? = null,
    val status: Status? = null,
    val language: String? = null,
    val genres: List<String>? = null
) : BaseEntity() {

}