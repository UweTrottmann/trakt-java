package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ListReorderResponse(
    val updated: Int? = null,
    @Json(name = "skipped_ids")
    val skippedIds: List<Long>? = null
)