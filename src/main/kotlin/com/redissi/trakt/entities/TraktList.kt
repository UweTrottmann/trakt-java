package com.redissi.trakt.entities

import com.redissi.trakt.enums.ListPrivacy
import com.redissi.trakt.enums.SortBy
import com.redissi.trakt.enums.SortHow
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class TraktList(
    val ids: ListIds? = null,
    val name: String? = null,
    val description: String? = null,
    val privacy: ListPrivacy? = null,
    @Json(name = "display_numbers")
    val displayNumbers: Boolean? = null,
    @Json(name = "allow_comments")
    val allowComments: Boolean? = null,
    @Json(name = "sort_by")
    val sortBy: SortBy? = null,
    @Json(name = "sort_how")
    val sortHow: SortHow? = null,
    @Json(name = "created_at")
    val createdAt: OffsetDateTime? = null,
    @Json(name = "updated_at")
    val updatedAt: OffsetDateTime? = null,
    @Json(name = "item_count")
    val itemCount: Int? = null,
    @Json(name = "comment_count")
    val commentCount: Int? = null,
    val likes: Int? = null,
    val user: User? = null
)