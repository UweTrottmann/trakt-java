package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Comment;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import java.util.List;

public interface Comments {

    /**
     * <b>OAuth Required</b>
     *
     * <p> Add a new comment to a movie, show, episode, or list. If you add a review, it needs to be at least 200 words.
     * Also make sure to allow and encourage spoilers to be indicated in your app.
     *
     * @param comment A {@link com.uwetrottmann.trakt.v2.entities.Comment} with either a movie, show or episode set,
     * plus comment and spoiler or review flags.
     */
    @POST("/comments")
    Comment post(
            @Body Comment comment
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns a single comment and indicates how many replies it has. Use GET /comments/:id/replies to get the
     * actual replies.
     *
     * @param id A specific comment ID. Example: 417.
     */
    @GET("/comments/{id}")
    Comment get(
            @Path("id") int id
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Update a single comment created within the last hour. The OAuth user must match the author of the comment in
     * order to update it.
     *
     * @param id A specific comment ID. Example: 417.
     * @param comment A {@link com.uwetrottmann.trakt.v2.entities.Comment} with comment and spoiler or review flags.
     */
    @PUT("/comments/{id}")
    Comment update(
            @Path("id") int id,
            @Body Comment comment
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Delete a single comment created within the last hour. This also effectively removes any replies this comment
     * has. The OAuth user must match the author of the comment in order to delete it.
     *
     * @param id A specific comment ID. Example: 417.
     */
    @DELETE("/comments/{id}")
    Response delete(
            @Path("id") int id
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all replies for a comment. It is possible these replies could have replies themselves, so in that
     * case you would just call GET /comment/:id/replies again with the new comment_id.
     *
     * @param id A specific comment ID. Example: 417.
     */
    @GET("/comments/{id}/replies")
    List<Comment> getReplies(
            @Path("id") int id
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Add a new reply to an existing comment. Also make sure to allow and encourage spoilers to be indicated in
     * your app.
     *
     * @param id A specific comment ID. Example: 417.
     * @param comment A {@link com.uwetrottmann.trakt.v2.entities.Comment} with comment and spoiler or review flags.
     */
    @POST("/comments/{id}/replies")
    Comment postReply(
            @Path("id") int id,
            @Body Comment comment
    );

}
