package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SyncPerson(
    val ids: PersonIds? = null,
    val name: String? = null
)