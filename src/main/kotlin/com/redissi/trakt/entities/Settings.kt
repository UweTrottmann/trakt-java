package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Settings(
    val user: User? = null,
    val account: Account? = null,
    val connections: Connections? = null,
    @Json(name = "sharing_text")
    val sharingText: SharingText? = null
)