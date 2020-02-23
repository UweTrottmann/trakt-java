package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

/**
 * Type to use for parsing check in error response (call [com.redissi.trakt.Trakt.checkForCheckinError]
 * with this class) if you get a 409 HTTP status code when checking in.
 */
@JsonClass(generateAdapter = true)
class CheckinError(
    /** Timestamp which is when the user can check in again.  */
    @Json(name = "expires_at")
    val expiresAt: OffsetDateTime? = null
)