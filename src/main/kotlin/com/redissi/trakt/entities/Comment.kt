package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class Comment(
    val id: Int? = null,
    @Json(name = "parent_id")
    val parentId: Int? = null,
    @Json(name = "created_at")
    val createdAt: OffsetDateTime? = null,
    @Json(name = "updated_at")
    val updatedAt: OffsetDateTime? = null,
    val comment: String? = null,
    val spoiler: Boolean? = null,
    val review: Boolean? = null,
    val replies: Int? = null,
    val likes: Int? = null,
    @Json(name = "user_rating")
    val userRating: Int? = null,
    val user: User? = null,
    // for posting
    val movie: Movie? = null,
    val show: Show? = null,
    val episode: Episode? = null
) {

    companion object {
        /**
         * Build a movie comment.
         */
        fun build(movie: Movie?, comment: String?, spoiler: Boolean, review: Boolean) =
            Comment(comment = comment, spoiler = spoiler, review = review, movie = movie)

        /**
         * Build a show comment.
         */
        fun build(show: Show?, comment: String?, spoiler: Boolean, review: Boolean) =
            Comment(comment = comment, spoiler = spoiler, review = review, show = show)

        /**
         * Build an episode comment.
         */
        fun build(episode: Episode?, comment: String?, spoiler: Boolean, review: Boolean) =
            Comment(comment = comment, spoiler = spoiler, review = review, episode = episode)

        /**
         * Build an updated comment.
         */
        fun build(comment: String?, spoiler: Boolean, review: Boolean) =
            Comment(comment = comment, spoiler = spoiler, review = review)
    }

}