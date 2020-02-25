package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class User(
    val username: String? = null,
    @Json(name = "private")
    val isPrivate: Boolean? = null,
    val name: String? = null,
    /** If a user is a regular VIP.  */
    val vip: Boolean? = null,
    /** If a user is an execute producer.  */
    @Json(name = "vip_ep")
    val vipEp: Boolean? = null,
    val ids: UserIds? = null,
    // full
    @Json(name = "joined_at")
    val joinedAt: OffsetDateTime? = null,
    val location: String? = null,
    val about: String? = null,
    val gender: String? = null,
    val age: Int = 0,
    val images: Images? = null
) {
    @JsonClass(generateAdapter = true)
    class UserIds(
        val slug: String? = null
    )
}