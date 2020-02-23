package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class AccessToken(
    @Json(name = "access_token")
    val accessToken: String? = null,
    @Json(name = "token_type")
    val tokenType: String? = null,
    @Json(name = "expires_in")
    val expiresIn: Int? = null,
    @Json(name = "refresh_token")
    val refreshToken: String? = null,
    val scope: String? = null
)