package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SyncResponse(
    val added: SyncStats? = null,
    val existing: SyncStats? = null,
    val deleted: SyncStats? = null,
    @Json(name = "not_found")
    val notFound: SyncErrors? = null
)