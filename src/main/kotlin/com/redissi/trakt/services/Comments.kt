package com.redissi.trakt.services

import com.redissi.trakt.entities.Comment
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Comments {
    /**
     * **OAuth Required**
     *
     *
     *  Add a new comment to a movie, show, episode, or list. If you add a review, it needs to be at least 200 words.
     * Also make sure to allow and encourage spoilers to be indicated in your app.
     *
     * @param comment A [Comment] with either a movie, show or episode set, plus comment and spoiler or review
     * flags.
     */
    @POST("comments")
    suspend fun post(
        @Body comment: Comment?
    ): Comment?

    /**
     * **OAuth Required**
     *
     *
     *  Returns a single comment and indicates how many replies it has. Use GET /comments/:id/replies to get the
     * actual replies.
     *
     * @param id A specific comment ID. Example: 417.
     */
    @GET("comments/{id}")
    suspend fun get(
        @Path("id") id: Int
    ): Comment?

    /**
     * **OAuth Required**
     *
     *
     *  Update a single comment created within the last hour. The OAuth user must match the author of the comment in
     * order to update it.
     *
     * @param id A specific comment ID. Example: 417.
     * @param comment A [Comment] with comment and spoiler or review flags.
     */
    @PUT("comments/{id}")
    suspend fun update(
        @Path("id") id: Int,
        @Body comment: Comment?
    ): Comment?

    /**
     * **OAuth Required**
     *
     *
     *  Delete a single comment created within the last hour. This also effectively removes any replies this comment
     * has. The OAuth user must match the author of the comment in order to delete it.
     *
     * @param id A specific comment ID. Example: 417.
     */
    @DELETE("comments/{id}")
    suspend fun delete(
        @Path("id") id: Int
    ): Response<Unit>

    /**
     * **OAuth Required**
     *
     *
     *  Returns all replies for a comment. It is possible these replies could have replies themselves, so in that
     * case you would just call GET /comment/:id/replies again with the new comment_id.
     *
     * @param id A specific comment ID. Example: 417.
     */
    @GET("comments/{id}/replies")
    suspend fun replies(
        @Path("id") id: Int
    ): List<Comment>?

    /**
     * **OAuth Required**
     *
     *
     *  Add a new reply to an existing comment. Also make sure to allow and encourage spoilers to be indicated in
     * your app.
     *
     * @param id A specific comment ID. Example: 417.
     * @param comment A [Comment] with comment and spoiler or review flags.
     */
    @POST("comments/{id}/replies")
    suspend fun postReply(
        @Path("id") id: Int,
        @Body comment: Comment?
    ): Comment?
}