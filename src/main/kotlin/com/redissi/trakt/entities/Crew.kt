package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Crew(
    var writing: List<CrewMember>? = null,
    var production: List<CrewMember>? = null,
    var directing: List<CrewMember>? = null,
    @Json(name = "costume & make-up")
    var costumeAndMakeUp: List<CrewMember>? = null,
    var art: List<CrewMember>? = null,
    var sound: List<CrewMember>? = null,
    var camera: List<CrewMember>? = null
)