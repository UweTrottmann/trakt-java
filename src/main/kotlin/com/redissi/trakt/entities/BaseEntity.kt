package com.redissi.trakt.entities

import com.squareup.moshi.Json
import java.time.OffsetDateTime

abstract class BaseEntity {

    abstract val title: String?
    // extended info
    abstract val overview: String?
    abstract val rating: Double?
    abstract val votes: Int?
    @Json(name = "updated_at")
    abstract val updatedAt: OffsetDateTime?
    @Json(name = "available_translations")
    abstract val availableTranslations: List<String>?

}